package com.example.mytaxi
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.example.mytaxi.ui.theme.MyTaxiTheme
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.bindgen.Expected
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.GetLocationCallback
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationError
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.mytaxi.utis.MapScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            MyTaxiTheme(darkTheme = darkTheme) {
                MapScreen()
            }
        }
    }
}

fun requestLocation(onLocationRequested: (com.mapbox.common.location.Location?) -> Unit) {
    val request: LocationProviderRequest = LocationProviderRequest.Builder()
        .apply {

                IntervalSettings.Builder()
                    .interval(0L)
                    .minimumInterval(0L)
                    .maximumInterval(0L)
                    .build()
            accuracy(AccuracyLevel.HIGHEST)
            displacement(0F)
        }.build()

    val locationService: LocationService = LocationServiceFactory.getOrCreate()
    val expected: Expected<LocationError, DeviceLocationProvider> = locationService.getDeviceLocationProvider(request)
    val provider: DeviceLocationProvider? = expected.value
    provider?.getLastLocation(callback = object : GetLocationCallback {
        override fun run(location: com.mapbox.common.location.Location?) {
            onLocationRequested(location)
        }
    })
}




