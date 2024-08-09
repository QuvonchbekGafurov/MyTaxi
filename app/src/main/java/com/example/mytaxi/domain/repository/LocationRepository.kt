package com.example.mytaxi.domain.repository

import com.example.mytaxi.domain.model.DataLocation


interface LocationRepository {
    suspend fun getLocation(id: Int): DataLocation
}