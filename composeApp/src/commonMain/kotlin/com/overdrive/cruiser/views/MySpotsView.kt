package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.BookingsViewModel
import com.overdrive.cruiser.models.MySpotsViewModel
import com.overdrive.cruiser.models.Spot
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.accessibility_on
import cruiser.composeapp.generated.resources.weather_on
import cruiser.composeapp.generated.resources.activity
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource

@Composable
fun MySpotsView(mySpotsViewModel: MySpotsViewModel, bookingsViewModel: BookingsViewModel,
                onAddSpotClick: () -> Unit, onActivityClick: () -> Unit) {
    var selectedSpot by remember { mutableStateOf<Spot?>(null) }

    Column {
        if (selectedSpot != null) {
            MySpotInformation(mySpotsViewModel, selectedSpot!!, onAddSpotClick) { selectedSpot = null }
        } else {
            MySpotsList(mySpotsViewModel, bookingsViewModel, onAddSpotClick, onSpotSelected = {
                selectedSpot = it
            }, onActivityClick = onActivityClick)
        }
    }
}

@Composable
fun MySpotsList(mySpotsViewModel: MySpotsViewModel, bookingsViewModel: BookingsViewModel,
                onAddSpotClick: () -> Unit, onSpotSelected: (Spot) -> Unit, onActivityClick: () -> Unit) {
    val allSpots by mySpotsViewModel.spots.collectAsState()
    val devSpots = allSpots.filter { it.ownerId == "dev3" }

    val allBookings by bookingsViewModel.bookings.collectAsState()
    val currentTime = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) }

    val devSpotIds = devSpots.map { it.id }
    val ownerBookings = allBookings.filter { booking -> booking.parkingSpotId in devSpotIds }
        .filter { it.endTime > currentTime && it.startTime < currentTime }

    val bookedSpots = ownerBookings.map { booking ->
        devSpots.first { it.id == booking.parkingSpotId }
    }
    val unbookedSpots = devSpots.filter { it !in bookedSpots }

    LaunchedEffect(Unit) {
        bookingsViewModel.updateBookings()
        mySpotsViewModel.updateSpots()
    }

    Column(modifier = Modifier.background(color = Color(0xFFF5F5F5))) {
        SpotOnTopBar("My Spots", clickableIcon = { SpotOnActivityIcon(onActivityClick) })

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
        ) {
            Column {
                val tabs = listOf("Available", "Booked")
                var selectedTab by remember { mutableStateOf(0) }
                SpotOnTabRow(
                    tabs = tabs,
                    selectedTabIndex = selectedTab,
                    { selectedTab = it }
                ) { i -> selectedTab = i }
                when (selectedTab) {
                    0 -> SpotOnSpotList(unbookedSpots, Color(0xFF006400), onClick = onSpotSelected)
                    1 -> SpotOnSpotList(bookedSpots, Color.LightGray, onClick = onSpotSelected)
                }
            }

            Button(
                onClick = onAddSpotClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .padding(bottom = 24.dp)
                    .size(64.dp)
                    .shadow(4.dp, CircleShape)
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun MySpotInformation(mySpotsViewModel: MySpotsViewModel, spot: Spot, onAddSpotClick: () -> Unit,
                      onBackClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column {
        SpotOnTopBar("My Spot", onBackClick = onBackClick)

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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        showDialog = true
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, Color(0xFFF9784B), RoundedCornerShape(12.dp))
            ) {
                Text(
                    text = "Delete Spot",
                    color = Color(0xFFF9784B),
                )
            }
        }
    }

    if (showDialog) {
        SpotOnAlertDialog(
            "Confirm Deletion",
            "Are you sure you want to delete this spot?",
            onClick = {
                onBackClick()
                scope.launch {
                    spot.let {
                        mySpotsViewModel.deleteSpot(spot)
                        mySpotsViewModel.updateSpots()
                    }
                    showDialog = false
                }
            },
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
fun SpotOnActivityIcon(onActivityClick: () -> Unit) {
    IconButton(
        onClick = onActivityClick,
        modifier = Modifier.size(24.dp)
    ) {
        Image(
            imageVector = vectorResource(Res.drawable.activity),
            contentDescription = null
        )
    }
}
