package com.example.mytaxi.utis

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlignHorizontalCenter
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.mytaxi.R
import com.example.mytaxi.ui.theme.bottomsheet_background
import com.example.mytaxi.ui.theme.content_secondary
import com.mapbox.maps.extension.style.expressions.dsl.generated.color
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Banknote
import io.github.alexzhirkevich.cupertino.icons.outlined.CheckmarkSquare
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronBackward
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronDown
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronUp
import io.github.alexzhirkevich.cupertino.icons.outlined.Francsign
import io.github.alexzhirkevich.cupertino.icons.outlined.HeartTextSquare
import io.github.alexzhirkevich.cupertino.icons.outlined.Location
import io.github.alexzhirkevich.cupertino.icons.outlined.Minus
import io.github.alexzhirkevich.cupertino.icons.outlined.NoteText
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus
import io.github.alexzhirkevich.cupertino.icons.outlined.Square3Layers3dDownRight
import io.github.alexzhirkevich.cupertino.icons.outlined.SquareAndArrowUp
import io.github.alexzhirkevich.cupertino.icons.outlined.SquareStack
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
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetContent = {
            Box (modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(30.dp))){
                Column(
                    modifier = Modifier
                        .background(bottomsheet_background)
                        .fillMaxWidth()
                        .padding(16.dp)


                ) {
                    MenuItem(
                        icon = Icons.Default.ToggleOff, // O'rnatilgan ikonani tanlash mumkin
                        title = "Tarif",
                        count = "6/8"
                    )
                    Divider(color = Color(0xFFCBD2E0), thickness = 1.dp, modifier = Modifier.padding(10.dp))
                    MenuItem(
                        icon = Icons.Default.ShoppingCart, // O'rnatilgan ikonani tanlash mumkin
                        title = "Buyurtmalar",
                        count = "0"
                    )
                    Divider(color = Color(0xFFCBD2E0), thickness = 1.dp, modifier = Modifier.padding(10.dp))
                    MenuItem(
                        icon = Icons.Default.RocketLaunch, // O'rnatilgan ikonani tanlash mumkin
                        title = "Bordur"
                    )
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
                            .size(56.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Plus,
                            contentDescription = null,
                            tint = content_secondary
                        )
                    }
                    CupertinoIconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .size(56.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Minus,
                            contentDescription = null,
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
                            .size(56.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                    ) {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Location,
                            contentDescription = null,
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
                        .size(56.dp)
                        .align(Alignment.CenterStart)
                        .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                ) {
                    CupertinoIcon(
                        imageVector = CupertinoIcons.Default.ChevronUp,
                        contentDescription = null,
                        tint = content_secondary,
                        modifier = Modifier
                            .offset(y = 4.dp)
                            .size(width = 15.dp, height = 12.dp)
                    )
                    CupertinoIcon(
                        imageVector = CupertinoIcons.Default.ChevronUp,
                        contentDescription = null,
                        tint = content_secondary,
                        modifier = Modifier
                            .offset(y = -4.dp)
                            .size(width = 15.dp, height = 12.dp)

                    )
                }
            Row(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.TopCenter)
                ,
            ) {
                Spacer(modifier = Modifier.width(24.dp)) // 10 dp space between buttons
                Box(modifier = Modifier
                    .background(shape = RoundedCornerShape(14.dp), color = Color.White)
                    .size(56.dp),
                ){
                    Image(
                        painter = painterResource(id = R.drawable.component_35), // Tasvir resursi
                        contentDescription = null ,// Tasvir tavsifi
                        modifier = Modifier
                            .size(56.dp)
                            .align(Alignment.Center)
                            .clickable { }
                    )
                }
                Spacer(modifier = Modifier.width(12.dp)) // 10 dp space between buttons

                androidx.compose.material3.Surface (modifier = Modifier
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(14.dp)
                    )){
                    ToggleButton(modifier = Modifier.background(color = Color.Transparent,
                    ))
                }
                Spacer(modifier = Modifier.width(12.dp)) // 10 dp space between buttons
                CupertinoIconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = Color.Green, shape = RoundedCornerShape(14.dp))
                        .border(2.dp, color = Color.White, shape = RoundedCornerShape(14.dp)) // Chegarani qora qilish

                ) {
                    Text(
                    text = "95",
                    fontSize = 20.sp, // Set text size to 20sp
                    fontWeight = FontWeight.Bold, // Set text style to bold
                    color = Color.Black, // Set text color to yellow
                    modifier = Modifier.size(24.dp)
                )
                }
                Spacer(modifier = Modifier.width(12.dp)) // 10 dp space between buttons

            }
        }
    }



}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun sshoww(){


    Box(modifier = Modifier
        .background(shape = RoundedCornerShape(14.dp), color = Color.White)
        .size(56.dp),
    ){
        Image(
            painter = painterResource(id = R.drawable.component_35), // SVG ikon
            contentDescription = null ,// Tasvir tavsifi
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.Center)
                .clickable { },

            )
    }

}



