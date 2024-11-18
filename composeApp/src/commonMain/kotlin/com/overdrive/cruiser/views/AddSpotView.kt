package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.overdrive.cruiser.endpoints.SearchBoxFetcher
import com.overdrive.cruiser.models.AddSpotViewModel
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.models.mapbox.Suggestion
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.accessibility_off
import cruiser.composeapp.generated.resources.accessibility_on
import cruiser.composeapp.generated.resources.weather_off
import cruiser.composeapp.generated.resources.weather_on
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AddSpotView(onBackClick: () -> Unit, onSpotAdded: () -> Unit, addSpotViewModel: AddSpotViewModel) {
    val scope = rememberCoroutineScope()
    var dollarsAnHour by remember { mutableStateOf("0.0") }
    var dollarsADay by remember { mutableStateOf("0.0") }
    var accessible by remember { mutableStateOf(true) }
    var sheltered by remember { mutableStateOf(true) }

    var buildingTypeExpanded by remember { mutableStateOf(false) }
    val buildingTypes = listOf("House", "Apartment", "Condo", "Other")
    var selectedBuildingType by remember { mutableStateOf(buildingTypes.first()) }
    var spotNumbers by remember { mutableStateOf("1") }
    val currentLocation by addSpotViewModel.currentLocation.collectAsState()
    val selectedAddress by addSpotViewModel.selectedAddress.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize().background(color = Color(0xFFF5F5F5))
    ) {
        SpotOnTopBar("Add Spot", onBackClick)

        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            Box(
                modifier = Modifier
                    .height(55.dp)
                    .shadow(12.dp, RoundedCornerShape(12.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Toggle Features:")

                    Spacer(modifier = Modifier.weight(1f))

                    SpotOnIconToggle(
                        checked = sheltered,
                        iconOn = Res.drawable.weather_on,
                        iconOff = Res.drawable.weather_off,
                        onCheckedChange = { newState ->
                            sheltered = newState
                        }
                    )

                    SpotOnIconToggle(
                        checked = accessible,
                        iconOn = Res.drawable.accessibility_on,
                        iconOff = Res.drawable.accessibility_off,
                        onCheckedChange = { newState ->
                            accessible = newState
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                SpotOnTextField(
                    label = "Add $/hour",
                    value = dollarsAnHour,
                    onValueChange = { dollarsAnHour = it },
                    modifier = Modifier
                        .height(55.dp)
                        .weight(0.45f)
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                SpotOnTextField(
                    label = "Add $/day",
                    value = dollarsADay,
                    onValueChange = { dollarsADay = it },
                    modifier = Modifier
                        .height(55.dp)
                        .weight(0.45f)
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AddSpotSearchBox(addSpotViewModel)

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Box(
                    modifier = Modifier
                        .height(55.dp)
                        .shadow(12.dp, RoundedCornerShape(12.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .clickable(onClick = { buildingTypeExpanded = !buildingTypeExpanded }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Building Type", fontSize = 12.sp, color = Color.Gray)
                            Text(selectedBuildingType)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text("▼", color = Color.Gray)
                    }
                }

                if (buildingTypeExpanded) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(12.dp))
                            .height(165.dp)
                            .background(Color.White)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        items(buildingTypes) { buildingType ->
                            Text(
                                text = buildingType,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable {
                                        selectedBuildingType = buildingType
                                        buildingTypeExpanded = false
                                    },
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            SpotOnTextField(
                label = "Add Spot Number(s), Ex. 1,4-6",
                value = spotNumbers,
                onValueChange = { spotNumbers = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            SpotOnSelectAvailability()

            Spacer(modifier = Modifier.height(16.dp))

            Text("Add photos of your spot")

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .size(175.dp, 125.dp)
                    .shadow(8.dp)
                    .background(color = Color.LightGray)
            ) {
                Button(
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .size(56.dp)
                ) {
                    Text(
                        text = "+",
                        color = Color.DarkGray,
                        fontSize = 30.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Add videos of your spot (validation)")

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .size(175.dp, 125.dp)
                    .shadow(8.dp)
                    .background(color = Color.LightGray)
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .size(56.dp)
                ) {
                    Text(
                        text = "+",
                        color = Color.DarkGray,
                        fontSize = 30.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val createdSpotNumbers = parseNumberRange(spotNumbers)

                    for (spotNumber in createdSpotNumbers) {
                        val spot = Spot(
                            "999", "dev3", "$selectedAddress $spotNumber",
                            currentLocation.longitude, currentLocation.latitude
                        )

                        scope.launch {
                            addSpotViewModel.createSpot(spot)
                        }
                    }

                    onSpotAdded()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF9784B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("Submit", color = Color.White)
            }
        }
    }
}

@Composable
fun SpotOnIconToggle(
    checked: Boolean,
    iconOn: DrawableResource,
    iconOff: DrawableResource,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        content = {
            Image(
                modifier = modifier.size(17.dp),
                imageVector = vectorResource(if (checked) iconOn else iconOff),
                contentDescription = null
            )
        }
    )
}

@Composable
fun AddSpotSearchBox(addSpotViewModel: AddSpotViewModel) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val suggestions by addSpotViewModel.suggestions.collectAsState()
    val query by addSpotViewModel.query.collectAsState()
    val suggestionGenerator = remember { SearchBoxFetcher() }

    Column {
        TextField(
            label = { Text("Add Address") },
            value = query,
            onValueChange = {
                addSpotViewModel.updateQuery(it)
                if (it.trim().isNotEmpty()) {
                    scope.launch {
                        addSpotViewModel.updateSuggestions(
                            suggestionGenerator.fetch(it)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.White,
                cursorColor = Color.Gray,
                focusedLabelColor = Color.DarkGray,
                unfocusedLabelColor = Color.Gray,
                textColor = Color.DarkGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        )

        if (suggestions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .height(165.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                items(suggestions) { suggestion ->
                    Text(
                        text = suggestion.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                scope.launch {
                                    suggestionGenerator.fetch(suggestion)?.let { feature ->
                                        addSpotViewModel.updateCurrentLocation(
                                            Coordinate(
                                                feature.geometry.coordinates[0],
                                                feature.geometry.coordinates[1]
                                            )
                                        )
                                    }
                                }
                                focusManager.clearFocus()
                                addSpotViewModel.updateQuery(suggestion.name)
                                addSpotViewModel.updateSuggestions(emptyList())
                                addSpotViewModel.updateSelectedAddress(suggestion.name)
                            },
                        color = Color.DarkGray,
                    )
                }
            }
        }

    }
}

@Composable
fun SpotOnSelectAvailability() {
    var selectedWeekdays by remember { mutableStateOf(mutableMapOf(
        "M" to true,
        "Tu" to true,
        "W" to true,
        "Th" to true,
        "F" to true,
        "Sa" to false,
        "Su" to false
    )) }

    var range by remember { mutableStateOf(32f..64f) }
    val startMinutes = (range.start * 15).toInt()
    val endMinutes = (range.endInclusive * 15).toInt()

    val startHour = startMinutes / 60
    val startMinute = startMinutes % 60
    val endHour = endMinutes / 60
    val endMinute = endMinutes % 60

    val startHour12 = if (startHour % 12 == 0) 12 else startHour % 12
    val startMeridian = if (startHour < 12) "AM" else "PM"
    val endHour12 = if (endHour % 12 == 0) 12 else endHour % 12
    val endMeridian = if (endHour < 12 || endHour == 24) "AM" else "PM"

    Column {
        Text("Set Availability")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for ((weekday, value) in selectedWeekdays) {
                val borderColor = if (value) Color(0xFFF9784B) else Color.LightGray
                val fontWeight = if (value) FontWeight.Bold else FontWeight.Thin

                Button(
                    modifier = Modifier
                        .weight(1f / 7)
                        .height(55.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = {
                        selectedWeekdays = selectedWeekdays.toMutableMap().apply {
                            this[weekday] = !value
                        }
                    }
                ) {
                    Text(
                        weekday.first().toString(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontWeight = fontWeight)
                    )
                }
            }
        }

        Column {
            RangeSlider(
                value = range,
                onValueChange = {
                    range = it

                },
                valueRange = 0f..96f,
                steps = 95,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFF9784B),
                    activeTrackColor = Color(0xFFF9784B),
                    inactiveTrackColor = Color.LightGray,
                    inactiveTickColor = Color.Transparent,
                    activeTickColor = Color.Transparent
                )
            )

            Text(
                text = "${startHour12}:${startMinute.toString().padStart(2, '0')}" +
                        " $startMeridian - ${endHour12}:${
                            endMinute.toString().padStart(2, '0')
                        } $endMeridian",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

fun parseNumberRange(input: String): List<Int> {
    return input.split(",").map { it.trim() }
        .flatMap { part ->
            if ("-" in part) {
                val rangeBounds = part.split("-").map { it.trim(); it.toInt() }
                (rangeBounds[0]..rangeBounds[1]).toList()
            } else {
                listOf(part.toInt())
            }
        }
        .filter { it > 0 }
}
