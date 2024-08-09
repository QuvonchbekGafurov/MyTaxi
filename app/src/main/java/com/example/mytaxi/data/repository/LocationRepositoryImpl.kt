package com.example.mytaxi.data.repository

import com.example.mytaxi.domain.model.DataLocation
import com.example.mytaxi.domain.repository.LocationRepository

// data/repository/LocationRepositoryImpl.kt


class LocationRepositoryImpl() : LocationRepository {
    override suspend fun getLocation(id: Int): DataLocation {
        TODO("Not yet implemented")
    }
}

