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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.BookingsViewModel
import com.overdrive.cruiser.models.MySpotsViewModel
import com.overdrive.cruiser.models.Spot
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.accessibility_on
import cruiser.composeapp.generated.resources.weather_on
import cruiser.composeapp.generated.resources.activity
import cruiser.composeapp.generated.resources.image_placeholder
import cruiser.composeapp.generated.resources.user_warning
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
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

@Composable
fun MySpotInformation(mySpotsViewModel: MySpotsViewModel, spot: Spot, onAddSpotClick: () -> Unit,
                          onBackClick: () -> Unit) {

    val spots by mySpotsViewModel.spots.collectAsState()
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    Column {
        SpotOnTopBar("My Spot", onBackClick = onBackClick)
        LaunchedEffect(Unit) {
            mySpotsViewModel.updateSpots()
        }

        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFF5F5F5))
                    .align(Alignment.Center)
            ) {
                if (spot == null) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "No spots yet, add one!",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Row { // $ per hour and per day
                                SpotOnField(
                                    modifier = Modifier
                                        .height(55.dp)
                                        .weight(0.45f)
                                        .shadow(8.dp, RoundedCornerShape(12.dp))
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .clip(RoundedCornerShape(12.dp))
                                ) {
                                    Text(
                                        "$5/hour",
                                        modifier = Modifier.padding(16.dp),
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                SpotOnField(
                                    modifier = Modifier
                                        .height(55.dp)
                                        .weight(0.45f)
                                        .shadow(8.dp, RoundedCornerShape(12.dp))
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .clip(RoundedCornerShape(12.dp))
                                ) {
                                    Text(
                                        "$50/day",
                                        modifier = Modifier.padding(16.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))


                            SpotOnField(modifier = Modifier.fillMaxWidth()) { // Popularity
                                Text(
                                    "226 total bookings",
                                    modifier = Modifier.padding(16.dp),
                                )
                            }
                            SpotOnField(modifier = Modifier.fillMaxWidth()) { // User's feedback
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Check feedback from other users")
                                    Image(
                                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                        "Forward arrow",
                                        modifier = Modifier.height(20.dp).width(20.dp)
                                    )
                                }
                            }

                            // Weekdays
                            Spacer(modifier = Modifier.height(16.dp))
                            AvailableDays()
                            Spacer(modifier = Modifier.height(8.dp))
                            SpotOnField(modifier = Modifier.fillMaxWidth()) { // Bookings
                                Text(
                                    "10AM to 5PM",
                                    modifier = Modifier.padding(16.dp),
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            // Rating and commodities
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart)
                                ) {
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

                        }
                        // Image carousel for the renting space pictures
                        ImageCarousel()

                        Column(modifier = Modifier.padding(16.dp)) {

                            SpotOnField(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    spot.address,
                                    modifier = Modifier.padding(16.dp),
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            // 24.dp instead of 32.dp because box above already have 8.dp padding
                            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                Box(
                                    modifier = Modifier
                                        .height(2.dp)
                                        .width(32.dp)
                                        .background(color = Color.Gray)
                                )
                            }
                            Spacer(modifier = Modifier.height(32.dp))

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

                            Spacer(modifier = Modifier.height(8.dp))
                            Button( // Delete Spot button
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
                                    .border(1.dp, Color(0xFFFF0000), RoundedCornerShape(12.dp))
                            ) {
                                Text(
                                    text = "Delete Spot",
                                    color = Color(0xFFFF0000),
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Button( // // Something happened button
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
                                    .border(1.dp, Color(0xFFFF0000), RoundedCornerShape(12.dp))
                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.padding(start = 20.dp))
                                    Image(
                                        imageVector = vectorResource(Res.drawable.user_warning),
                                        contentDescription = null,
                                        modifier = Modifier.width(26.dp).height(24.dp)
                                    )
                                    Spacer(Modifier.padding(start = 16.dp))
                                    Text(
                                        text = "Something happened? Tell us.",
                                        color = Color(0xFFFF0000),
                                    )
                                    Spacer(Modifier.weight(1f))
                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }

            }

            Column {
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = onAddSpotClick,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(64.dp)
                    ) {
                        Text("+")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
fun AvailableDays() {
    val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
    var selectedWeekdays by remember { mutableStateOf(mutableMapOf(
        "Mo" to true,
        "Tu" to true,
        "We" to true,
        "Th" to true,
        "F" to true,
        "Sa" to false,
        "Su" to false
    )) }

    val itemShape = RoundedCornerShape(4.dp)
    val orange = Color(234, 142, 112)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for ((weekday, selected) in selectedWeekdays) {
            val borderColor = if (selected) orange else Color.Gray
            val fontWeight = if (selected) FontWeight.Bold else FontWeight.Thin
            val bColor = if (selected) Color.White else Color.LightGray

            Text(
                weekday,
                modifier = Modifier
                    .weight(1f / 7)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = itemShape
                    )
                    .background(bColor, itemShape)
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = fontWeight)
            )
        }
    }
}

@Composable
fun ImageCarousel() {
    val itemList = listOf("image1", "image2", "image3", "image4", "image6")
    val pad = 12.dp
    LazyRow {
        item() {
            Spacer(modifier = Modifier.width(pad))
        }
        items(itemList) { item ->
            Image(
                painter = painterResource(Res.drawable.image_placeholder),
                contentDescription = item,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(200.dp).height(154.dp)
                    .padding(horizontal = 8.dp)
            )
        }
        item() {
            Spacer(modifier = Modifier.width(pad))
        }
    }
}