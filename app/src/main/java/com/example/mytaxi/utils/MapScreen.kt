package com.example.mytaxi.utils

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.mytaxi.R
import com.example.mytaxi.presentation.MapContent
import com.example.mytaxi.presentation.requestLocation
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
                    center(Point.fromLngLat(69.2610, 41.2919))
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
       val bottomSheetState = scaffoldState.bottomSheetState

    var zoomLevel by remember { mutableStateOf(14.0) } // Zoom darajasini saqlash uchun o'zgaruvchi


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = MaterialTheme.colorScheme.outlineVariant,
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetContent = {
            Box (modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(30.dp))){
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .padding(16.dp)


                ) {
                    MenuItem(
                        icon = painterResource(id = R.drawable.tariff), // O'rnatilgan ikonani tanlash mumkin
                        title = "Tarif",
                        count = "6/8"
                    )
                    Divider(color = MaterialTheme.colorScheme.onSecondary, thickness = 1.dp, modifier = Modifier.padding(10.dp))
                    MenuItem(
                        icon = painterResource(id = R.drawable.order_), // O'rnatilgan ikonani tanlash mumkin
                        title = "Buyurtmalar",
                        count = "0"
                    )
                    Divider(color = MaterialTheme.colorScheme.onSecondary, thickness = 1.dp, modifier = Modifier.padding(10.dp))
                    MenuItem(
                        icon =painterResource(id = R.drawable.rocket), // O'rnatilgan ikonani tanlash mumkin
                        title = "Bordur"
                    )
                }
            }
        },
    ){
        Box(
            modifier = Modifier.fillMaxSize()

        ){

                MapContent(
                    mapViewportState = mapviewPortState,
                    onMapLongClickListener = {
                        Log.i("Map-screen", "clicked on $it")
                        true
                    }
                )

            AnimatedVisibility(
                visible = false,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 100))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            scope.launch {
                                if (scaffoldState.bottomSheetState.isCollapsed) {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                        .background(Color.Black.copy(alpha = 0.5f))
                )
            }
            var isButtonVisible by remember { mutableStateOf(true)}
            Box(modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(start = 10.dp)) {

                AnimatedVisibility(
                    visible = isButtonVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInHorizontally(
                        initialOffsetX = { it }),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutHorizontally(
                        targetOffsetX = { it })
                ) {
                    Column(modifier = Modifier.align(alignment = Alignment.CenterEnd))
                    {
                        CupertinoIconButton(
                            onClick = {
                                zoomLevel += 1
                                mapviewPortState.flyTo(
                                    cameraOptions = CameraOptions.Builder()
                                        .apply {
                                            zoom(zoomLevel)
                                        }.build()
                                )
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .size(56.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .border(
                                    2.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(14.dp)
                                )
                        ) {
                            CupertinoIcon(
                                imageVector = CupertinoIcons.Default.Plus,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        CupertinoIconButton(
                            onClick = {
                                zoomLevel -= 1
                                mapviewPortState.flyTo(
                                    cameraOptions = CameraOptions.Builder()
                                        .apply {
                                            zoom(zoomLevel)
                                        }.build()
                                )
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .size(56.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .border(
                                    2.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(14.dp)
                                )
                        ) {
                            CupertinoIcon(
                                imageVector = CupertinoIcons.Default.Minus,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
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
                                                    zoom(zoomLevel)
                                                }.build()
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .size(56.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .border(
                                    2.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(14.dp)
                                )

                        ) {
                            CupertinoIcon(
                                imageVector = CupertinoIcons.Default.Location,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }

                    }
                }

            }
            LaunchedEffect(bottomSheetState.isExpanded) {
                isButtonVisible = !bottomSheetState.isExpanded
            }
            Box(modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)){
            AnimatedVisibility(
                visible = isButtonVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInHorizontally(initialOffsetX = { -it }),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutHorizontally(targetOffsetX = { -it })
            ) {
                CupertinoIconButton(
                    onClick = {
                        scope.launch {
                            bottomSheetState.expand()
                        }
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.CenterStart)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .border(
                            2.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(14.dp)
                        )

                ) {
                    CupertinoIcon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .offset(y = 4.dp)
                            .size(width = 24.dp, height = 24.dp)
                    )
                    CupertinoIcon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .offset(y = -4.dp)
                            .size(width = 24.dp, height = 24.dp)
                    )
                }
            }
                }
            Row(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.TopCenter)
                ,
            ) {
                Spacer(modifier = Modifier.width(24.dp))
                Box(modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        2.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(14.dp)
                    )
                ,
                ) {
                        Icon(
                            painter = painterResource(id = R.drawable.frame),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(24.dp)
                            ,
                        )
                }
                Spacer(modifier = Modifier.width(12.dp))

                androidx.compose.material3.Surface (modifier = Modifier
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(14.dp)
                    )){
                    ToggleButton(modifier = Modifier.background(color = MaterialTheme.colorScheme.outlineVariant,
                    ))
                }
                Spacer(modifier = Modifier.width(12.dp))
                CupertinoIconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = Color.Green, shape = RoundedCornerShape(14.dp))
                        .border(
                            2.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(14.dp)
                        )

                ) {
                    Text(
                    text = "95",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                }
                Spacer(modifier = Modifier.width(12.dp))

            }
        }
    }



}




