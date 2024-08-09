package com.example.mytaxi.data.repository

import com.example.mytaxi.data.room.LocationDao
import com.example.mytaxi.domain.model.DataLocation
import com.example.mytaxi.domain.repository.LocationRepository

// data/repository/LocationRepositoryImpl.kt


class LocationRepositoryImpl(private val locationDao: LocationDao) : LocationRepository {

    override suspend fun getLocation(id: Int): DataLocation {
        val locationEntity = locationDao.getLocationById(id) ?: throw Exception("Location not found")
        return DataLocation(
            longitude = locationEntity.longitude,
            latitude = locationEntity.latitude
        )
    }

    override suspend fun getAllLocations(): List<DataLocation> {
        return locationDao.getAllLocations().map {
            DataLocation(
                longitude = it.longitude,
                latitude = it.latitude
            )
        }
    }
}

