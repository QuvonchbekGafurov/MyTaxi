package com.example.mytaxi.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.host
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.StringValues
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.decodeFromStream
import okhttp3.RequestBody
import okio.IOException
import java.net.UnknownHostException

suspend inline fun <reified T,reified E>HttpClient.safeRequest(
    host: String,
    path: String,
    params: StringValues = StringValues.Empty,
    method: HttpMethod = HttpMethod.Get,
    body: Any? = null
) = try {
    val response = request {
        this.method = method
        this.host = host
        url {
            protocol = URLProtocol.HTTPS
            path(path)
            parameters.appendAll(params)
        }
        contentType(ContentType.Application.Json)
        if (body != null) setBody(body)
    }
    val stringBody=response.body<String>()
    Log.i("ktor-client", stringBody)
    if (response.status.isSuccess()){
        ApiResponse.Succes(data = json.decodeFromString<T>(stringBody))
    }
    else{
        ApiResponse.Error(errorCode = response.status.value, message = response.status.description, errorData = json.decodeFromString<E>(stringBody))
    }
} catch (e:SerializationException){
    e.printStackTrace()
    ApiResponse.Error(errorCode = -1, message = "Serialithation exception", errorData =null)

}catch (e:ServerResponseException){
    e.printStackTrace()
    ApiResponse.Error(errorCode = 500, message = "ServerResponseException", errorData =null)

}catch (e:UnknownHostException){
    e.printStackTrace()
    ApiResponse.Error(errorCode = -1000, message = "No internet connection", errorData =null)
}catch (e:IOException){
    e.printStackTrace()
    ApiResponse.Error(errorCode = -999, message = "Something went wrong", errorData =null)

} catch (e: Exception) {
    e.printStackTrace()
    ApiResponse.Error(errorCode = -10000, message = "Unknowm error", errorData =null)

}