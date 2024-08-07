package com.example.mytaxi.utis

import android.widget.ToggleButton
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.mytaxi.ui.theme.content_secondary
import com.mapbox.maps.Style
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus

@Composable
fun ToggleButton(modifier: Modifier) {
    var isActive by remember { mutableStateOf(false) }

    val activeColor = Color.Green
    val inactiveColor = Color(0xFFFFFFFF)
    val selectedTextColor = Color(0xFF000000)
    val unselectedTextColor = Color(0xFF000000)

    Row(
        modifier =modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.White)
            .padding(4.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    if (isActive) inactiveColor else Color.Red,
                    RoundedCornerShape(12.dp)
                )
                .clickable { isActive = false }
                ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Band",
                color = if (isActive) unselectedTextColor else selectedTextColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = if (isActive) FontWeight.Normal else FontWeight.Bold


            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    if (isActive) activeColor else inactiveColor,
                    RoundedCornerShape(12.dp)
                )
                .clickable { isActive = true }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Faol",
                color = if (isActive) selectedTextColor else unselectedTextColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}






