package com.overdrive.cruiser.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.overdrive.cruiser.endpoints.BookingEndpoint
import com.overdrive.cruiser.models.Booking
import com.overdrive.cruiser.models.BookingStatus
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.models.mapbox.DatePickerModel
import com.overdrive.cruiser.utils.TimeRange
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.accessibility_on
import cruiser.composeapp.generated.resources.saved_spots
import cruiser.composeapp.generated.resources.weather_on
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDetailView(datePickerModel: DatePickerModel, spot: Spot, onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    val bookingEndpoint = BookingEndpoint()
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
            .atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    )

    var selectedTimeRange by remember { mutableStateOf<TimeRange?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = isDatePickerVisible,
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) + fadeOut() using
                        SizeTransform(clip = false)
            }
        ) { visible ->
            if (visible) {
                Column(
                    modifier = Modifier.fillMaxSize().background(color = Color(0xFFF5F5F5))
                ) {
                    DatePickerView(
                        datePickerModel = datePickerModel,
                        onBack = { isDatePickerVisible = false },
                        onSelected = {
                            selectedTimeRange = it
                            isDatePickerVisible = false
                        }
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().background(color = Color(0xFFF5F5F5))
                ) {

                    SpotOnTopBar("Spot Details", onBack)

                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Row {
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

                        Spacer(modifier = Modifier.weight(1f))

                        RatingStarView(4)

                        Spacer(modifier = Modifier.weight(1f))

                        Row {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Bookmark",
                                tint = Color.Black
                            )
                        }
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

                        Spacer(modifier = Modifier.weight(1f))

                        Text("Book", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                        val tabs = listOf("Hourly", "Daily", "Schedule")
                        var hoursToBook by remember { mutableStateOf("1") }
                        var timeToBook by remember { mutableStateOf(1.hours) }

                        SpotOnTabRow(
                            tabs = tabs,
                            selectedTabIndex = selectedTab,
                            { selectedTab = it }
                        ) { i -> selectedTab = i }
                        when (selectedTab) {
                            0 -> SpotOnTextField(
                                value = hoursToBook,
                                onValueChange = { newValue ->
                                    hoursToBook = newValue
                                    val hours = newValue.toIntOrNull() ?: 1
                                    timeToBook = hours.hours
                                },
                                label = "Hours to Book",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            1 -> SpotOnTextField(
                                value = hoursToBook,
                                onValueChange = { newValue ->
                                    hoursToBook = newValue
                                    val days = newValue.toIntOrNull() ?: 1
                                    timeToBook = days.days
                                },
                                label = "Days to Book",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            2 -> SpotOnField(modifier = Modifier.fillMaxWidth().height(55.dp)) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val selectedDate = datePickerState.selectedDateMillis?.let {
                                        Instant.fromEpochMilliseconds(
                                            it + 1.days.inWholeMilliseconds
                                        ).toLocalDateTime(TimeZone.currentSystemDefault())
                                    }
                                    Text(
                                        text = "${selectedDate?.dayOfMonth} ${
                                            selectedDate?.month?.name?.lowercase()?.capitalize()
                                        } ${selectedDate?.year}"
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    IconButton(onClick = { isDatePickerVisible = true }) {
                                        Icon(
                                            imageVector = vectorResource(Res.drawable.saved_spots),
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }

                        // Displaying content below based on the selected tab
                        Box(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column {
                                Text("Subtotal: $0.00", color = Color.LightGray)
                                Text("Booking fee: $0.00", color = Color.LightGray)
                            }
                        }

                        SpotOnField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                        ) {
                            StripePaymentView(
                                amount = 50,
                                onComplete = {
                                    // TODO: since we aren't including seconds, we subtract 1 minute from the start time
                                    val startTime =
                                        selectedTimeRange?.startTime() ?: Clock.System.now()
                                            .minus(1.minutes)
                                            .toLocalDateTime(TimeZone.currentSystemDefault())
                                    val endTime = selectedTimeRange?.endTime() ?: Clock.System.now()
                                        .plus(timeToBook)
                                        .toLocalDateTime(TimeZone.currentSystemDefault())
                                    val booking = Booking(
                                        id = "1234",
                                        parkingSpotId = spot.id,
                                        userId = "1234",
                                        startTime = startTime,
                                        endTime = endTime,
                                        totalPrice = 0.0,
                                        bookingStatus = BookingStatus.PENDING,
                                        vehicleLicensePlate = "1234"
                                    )
                                    scope.launch {
                                        bookingEndpoint.create(booking)
                                        onBack()
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
