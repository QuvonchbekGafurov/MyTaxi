package com.example.mytaxi.data

import com.mapbox.base.common.logger.model.Message

sealed interface ApiResponse<T,E> {
    data class Succes<T>(val data:T):ApiResponse<T,Nothing>
    data class Error<E>(val errorCode:Int,val message: String,val errorData:E?=null):ApiResponse<Nothing,E>
}