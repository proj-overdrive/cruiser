package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.house_image
import cruiser.composeapp.generated.resources.spot_on_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun GetStartedView(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(modifier = Modifier
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
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(54.dp))

            Button(
                onClick = { onGetStartedClick() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF9784B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("Get Started", color = Color.White)
            }

            Button(
                onClick = { onGetStartedClick() },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                elevation = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Already have an account", color = Color(0xFFF9784B))
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(Res.drawable.house_image),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop
            )
        }
    }
}