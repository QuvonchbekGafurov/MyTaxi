package com.example.mytaxi.presentation.viewmodel


import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxi.domain.GetLocationUseCase
import com.example.mytaxi.presentation.intent.MapIntent
import com.example.mytaxi.presentation.models.MapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state

    fun processIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.RequestLocation -> requestCurrentLocation()
            is MapIntent.LocationReceived -> updateLocation(intent.location)
            is MapIntent.Error -> showError(intent.message)
        }
    }

    private fun requestCurrentLocation() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val location = getLocationUseCase()
                processIntent(MapIntent.LocationReceived(location))
            } catch (e: Exception) {
                processIntent(MapIntent.Error("Failed to get location"))
            }
        }
    }

    private fun updateLocation(location: com.mapbox.common.location.Location?) {
        _state.value = _state.value.copy(
            location = location,
            isLoading = false,
            error = null
        )
    }

    private fun showError(message: String) {
        _state.value = _state.value.copy(
            isLoading = false,
            error = message
        )
    }
}
