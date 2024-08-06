package com.example.mytaxi.utis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Location
@Composable
fun LocationButton(modifier: Modifier=Modifier,onClick:()->Unit) {
    CupertinoIconButton(
        onClick = onClick,
        modifier=modifier.background(color = Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        CupertinoIcon(
            imageVector = CupertinoIcons.Default.Location,
            contentDescription = null,
            modifier=Modifier.size(16.dp)
        )

    }
}

@Preview
@Composable
fun ShowLocationButton(){
    LocationButton(modifier = Modifier.size(38.dp), onClick = {})
}
