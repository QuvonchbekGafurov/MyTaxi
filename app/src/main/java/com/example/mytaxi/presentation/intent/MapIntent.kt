package com.example.mytaxi.presentation.intent//package com.example.mytaxi.presentation.intent

import com.mapbox.common.location.Location

sealed class MapIntent {
    object RequestLocation : MapIntent()
    data class LocationReceived(val location: Location?) : MapIntent()
    data class Error(val message: String) : MapIntent()
}
