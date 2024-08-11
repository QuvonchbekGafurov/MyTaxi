package com.example.mytaxi.presentation.models

import com.mapbox.common.location.Location

data class MapState(
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
