package com.example.mytaxi.domain

import com.example.mytaxi.data.LocationRepository
import com.mapbox.common.location.Location
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Location? {
        return locationRepository.getLastLocation()
    }
}
