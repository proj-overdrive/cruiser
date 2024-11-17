package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.house_image
import cruiser.composeapp.generated.resources.spot_on_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SpotOnLoginBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.house_image),
            contentDescription = null,
            modifier = modifier.fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun SpotOnBranding(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .height(150.dp)
                .padding(16.dp, 80.dp, 16.dp, 0.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.spot_on_logo),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
            )
        }

        Text(
            "The Parking Revolution",
            fontSize = 20.sp,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}
