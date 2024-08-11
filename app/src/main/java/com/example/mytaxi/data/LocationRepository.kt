package com.example.mytaxi.data


import com.mapbox.common.location.Location

interface LocationRepository {
    suspend fun getLastLocation(): Location?
}
