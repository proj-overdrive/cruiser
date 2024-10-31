package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cruiser.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import cruiser.composeapp.generated.resources.car_icon

@Composable
fun SpotOnTopBar(title: String, onBackClick: (() -> Unit)? = null) {
    TopAppBar(
        backgroundColor = Color.White,
        modifier = Modifier.shadow(8.dp),
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (onBackClick != null) {
                    // only include back arrow if onBackClick is provided
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }

                Text(
                    text = title,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )

                Image(
                    painter = painterResource(Res.drawable.car_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(60.dp)
                        .padding(16.dp, 8.dp)
                )
            }
        }
    )

    Spacer(modifier = Modifier.height(8.dp))
}