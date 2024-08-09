package com.example.mytaxi.presentation

// presentation/intent/LocationIntent.kt


sealed class LocationIntent {
    object FetchLocation : LocationIntent()
}
