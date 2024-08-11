package com.example.mytaxi.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ToggleButton(modifier: Modifier) {
    var isActive by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.outlineVariant)
            .padding(4.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    if (isActive) MaterialTheme.colorScheme.onPrimary else Color.Red,
                    RoundedCornerShape(12.dp)
                )
                .clickable { isActive = false }
                ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Band",
                color = if (isActive) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground,
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
                    if (isActive) Color.Green else MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(12.dp)
                )
                .clickable { isActive = true }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Faol",
                color = if (isActive) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground  ,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}






