package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.overdrive.cruiser.models.Booking
import com.overdrive.cruiser.models.BookingsViewModel
import com.overdrive.cruiser.models.Spot
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.booking_image
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun SpotOnSpotList(spots: List<Spot>, color: Color, onClick: (Spot) -> Unit = {}) {
    LazyColumn {
        items(spots) { spot ->
            SpotOnField(
                Modifier
                    .fillMaxWidth()
                    .clickable { onClick(spot) }
            ) {
                SpotOnListItem(address = spot.address, color = color)
            }
        }
    }
}

@Composable
fun SpotOnSpotList(bookings: List<Booking>, viewModel: BookingsViewModel, color: Color,
                   onClick: (Booking) -> Unit = {}) {
    LazyColumn {
        items(bookings) { booking ->
            SpotOnField(
                Modifier
                    .fillMaxWidth()
                    .clickable { onClick(booking) }
            ) {
                val scope = rememberCoroutineScope()
                var spot by remember { mutableStateOf<Spot?>(null) }

                LaunchedEffect(booking.parkingSpotId) {
                    if (spot == null) {
                        scope.launch {
                            spot = viewModel.getSpotForBooking(booking.parkingSpotId)
                        }
                    }
                }
                SpotOnListItem(address = spot?.address ?: "...Fetching Address", color = color)
            }
        }
    }
}

@Composable
fun SpotOnListItem(address: String, color: Color) {
    Box(
        Modifier.drawBehind {
            drawRect(
                color = color,
                topLeft = Offset(0f, 0f),
                size = Size(64f, size.height)
            )
        }
    ) {
        Row(
            Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(Res.drawable.booking_image),
                contentDescription = "Photo of spot",
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Summer House", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = address,
                    color = Color.Gray,
                    style = TextStyle(fontSize = 12.sp),
                    modifier = Modifier
                        .width(192.dp)
                        .padding(bottom = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Right Arrow",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(36.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
