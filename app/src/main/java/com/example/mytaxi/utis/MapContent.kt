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



