package com.example.mytaxi.utis

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.mytaxi.requestLocation
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mytaxi.ui.theme.content_secondary
import com.mapbox.maps.extension.style.expressions.dsl.generated.color
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Location
import io.github.alexzhirkevich.cupertino.icons.outlined.Minus
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen() {

        val context = LocalContext.current
        val mapviewPortState = rememberMapViewportState(
            init = {
                setCameraOptions() {
                    zoom(2.0)
                    center(Point.fromLngLat(111.0, 39.5))
                    pitch(0.0)
                    bearing(0.0)
                }
            }
        )
        val locationLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.any { permission -> permission.value }) {
                    requestLocation { location ->
                        location?.let {
                            mapviewPortState.flyTo(
                                cameraOptions = CameraOptions.Builder()
                                    .apply {
                                        center(
                                            Point.fromLngLat(
                                                it.longitude,
                                                it.latitude
                                            )
                                        )
                                        zoom(14.0)
                                    }.build()
                            )
                        }
                    }
                }
            }

        LaunchedEffect(Unit) {
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                mapviewPortState
            } else {
                locationLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }



       val scaffoldState= rememberBottomSheetScaffoldState()
       val scope= rememberCoroutineScope()


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Box (modifier = Modifier.padding(10.dp).clip(RoundedCornerShape(30.dp))){
                Column(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxWidth()

                ) {
                    CupertinoIconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Minus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = content_secondary
                        )
                    }
                    CupertinoIconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Minus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = content_secondary
                        )
                    }
                    CupertinoIconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Minus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = content_secondary
                        )
                    }

                }
            }
        },
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
        ){

                MapContent(
                    mapViewportState = mapviewPortState,
                    onMapLongClickListener = {
                        Log.i("Map-screen", "clicked on $it")
                        true
                    }
                )
                Column(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
                    CupertinoIconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Plus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = content_secondary
                        )
                    }
                    CupertinoIconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Minus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = content_secondary
                        )
                    }
                    CupertinoIconButton(
                        onClick = {

                            requestLocation { location ->
                                location?.let {
                                    mapviewPortState.flyTo(
                                        cameraOptions = CameraOptions.Builder()
                                            .apply {
                                                center(
                                                    Point.fromLngLat(
                                                        it.longitude,
                                                        it.latitude
                                                    )
                                                )
                                                zoom(14.0)
                                            }.build()
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Location,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                }
                CupertinoIconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterStart)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    CupertinoIcon(
                        imageVector = CupertinoIcons.Default.Plus,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = content_secondary
                    )
                }
        }
    }



}


