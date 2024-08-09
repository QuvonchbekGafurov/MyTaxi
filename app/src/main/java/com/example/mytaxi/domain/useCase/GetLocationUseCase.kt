package com.example.mytaxi.domain.useCase

import com.example.mytaxi.domain.repository.LocationRepository


class GetLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun execute(id: Int): Location {
        return locationRepository.getLocation(id)
    }
}