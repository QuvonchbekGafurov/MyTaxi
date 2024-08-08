package com.example.mytaxi.utis

import android.content.Context
import android.graphics.Paint
import android.text.style.LineBackgroundSpan.Standard
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.mytaxi.R
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.bindgen.Expected
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.GetLocationCallback
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationError
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxMap
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
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod
import com.mapbox.maps.plugin.LocationPuck2D

@Composable
fun MapContent(
    mapViewportState: MapViewportState,
    onMapLongClickListener:OnMapLongClickListener=DefaultSettingsProvider.defaultOnLongClickListener,
    content:(@Composable @MapboxMapComposable MapboxMapScope.()->Unit)?=null
){
    val backgroundColor = MaterialTheme.colorScheme.background // MaterialTheme rangini olish

    val locationComponentSettings =
        DefaultSettingsProvider.defaultLocationComponentSettings(
            LocalDensity.current.density
        ).toBuilder().apply {
            enabled=true
            puckBearingEnabled=true
            showAccuracyRing=true
            locationPuck = LocationPuck2D(
                topImage = ImageHolder.from(R.drawable.new_car_pin),
                bearingImage = ImageHolder.from(R.drawable.new_car_pin),
                shadowImage = ImageHolder.from(R.drawable.new_car_pin),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop { literal(0.0); literal(0.6) }
                    stop { literal(20.0); literal(1.0) }
                }.toJson()
            )
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
fun requestLocation(onLocationRequested: (Location?) -> Unit) {
    val request = LocationProviderRequest.Builder()
        .apply {
           // Yangilanish intervali
            accuracy(AccuracyLevel.HIGHEST) // Aniqlik darajasi
        }.build()

    val locationService = LocationServiceFactory.getOrCreate()
    val result = locationService.getDeviceLocationProvider(request)
    if (result.isValue) {
        val provider = result.value!!
        provider.getLastLocation(object : GetLocationCallback {
            override fun run(location: Location?) {
                onLocationRequested(location)
            }
        })
    } else {
        Log.e("LocationError", "Failed to get device location provider")
    }
}




