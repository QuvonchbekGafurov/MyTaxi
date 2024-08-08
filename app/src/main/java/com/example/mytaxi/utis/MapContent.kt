package com.example.mytaxi.utis

import android.content.Context
import android.graphics.Paint
import android.text.style.LineBackgroundSpan.Standard
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.mapbox.bindgen.Expected
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.GetLocationCallback
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationError
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.Style


import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.MapboxMapScope
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.scalebar.ScaleBar
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin
import com.mapbox.maps.extension.compose.style.projection.Projection
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod

@Composable
fun MapContent(
    mapViewportState: MapViewportState,
    onMapLongClickListener:OnMapLongClickListener=DefaultSettingsProvider.defaultOnLongClickListener,
    content:(@Composable @MapboxMapComposable MapboxMapScope.()->Unit)?=null
){
    val locationComponentSettings =
        DefaultSettingsProvider.defaultLocationComponentSettings(
            LocalDensity.current.density
        ).toBuilder().apply {
            enabled=true
            puckBearingEnabled=true
            showAccuracyRing=true
        }.build()
    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        locationComponentSettings = locationComponentSettings,
        style = {
            GenericStyle(style =Style.STANDARD, projection = Projection.MERCATOR)
        },
        scaleBar = {
        },
        compass = {
            Compass(modifier = Modifier.statusBarsPadding())
        },
        logo ={

        },
        attribution = {

        },
        onMapLongClickListener = onMapLongClickListener,
        mapViewportState = mapViewportState,
    ) {
        content?.let {
            it()
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




