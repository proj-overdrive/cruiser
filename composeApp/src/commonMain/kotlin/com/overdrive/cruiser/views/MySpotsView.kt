package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.MySpotsViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.accessibility_on
import cruiser.composeapp.generated.resources.weather_on
import org.jetbrains.compose.resources.vectorResource

@Composable
fun MySpotsView(mySpotsViewModel: MySpotsViewModel, onAddSpotClick: () -> Unit) {

    val spots by mySpotsViewModel.spots.collectAsState()

    Column {
        SpotOnTopBar("My Spot")
        LaunchedEffect(Unit) {
            mySpotsViewModel.updateSpots()
        }

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF5F5F5))
                .align(Alignment.Center)
                .padding(16.dp)
            ) {
                val spot = spots.firstOrNull { it.ownerId == "alyssa" }

                if (spot == null) {
                    Text(text = "No spots yet, add one!",
                        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                } else {

                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart)) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = vectorResource(Res.drawable.accessibility_on),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = vectorResource(Res.drawable.weather_on),
                                contentDescription = null
                            )
                        }

                        Row(modifier = Modifier.align(Alignment.Center)) {
                            RatingStarView(4)
                        }

                        Icon(
                            modifier = Modifier.size(24.dp).align(Alignment.CenterEnd),
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color.Black
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        SpotOnField(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(spot.address)
                            }
                        }
                        SpotOnField(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("$5/hour")
                                Spacer(modifier = Modifier.weight(1f))
                                Text("$40/day")
                            }
                        }
                        SpotOnField(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("226 total bookings")
                            }
                        }
                        Button(
                            onClick = { onAddSpotClick() },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF9784B)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Text(
                                text = "Edit Spot",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onAddSpotClick,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(CircleShape)
                            .size(64.dp)
                    ) {
                        Text("+")
                    }
                }
            }
        }
    }
}