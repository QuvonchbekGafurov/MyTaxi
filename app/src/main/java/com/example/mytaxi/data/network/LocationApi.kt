package com.example.mytaxi.data.network

import com.example.mytaxi.domain.model.DataLocation
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApi {
    @GET("locations/{id}")
    suspend fun getLocation(@Path("id") id: Int): DataLocation
}
