package com.example.mytaxi.data

import com.example.mytaxi.presentation.intent.MapIntent
import com.example.mytaxi.presentation.requestLocation
import com.mapbox.common.location.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor() : LocationRepository {
    override suspend fun getLastLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            requestLocation { location ->
                continuation.resume(location, null)
            }
        }
    }
}
