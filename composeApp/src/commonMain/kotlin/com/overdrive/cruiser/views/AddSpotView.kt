package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import org.jetbrains.compose.resources.vectorResource


@Composable
fun AddSpotView(onBackClick: () -> Unit, onSpotAdded: () -> Unit, addSpotViewModel: AddSpotViewModel) {
    val scope = rememberCoroutineScope()
    var dollarsAnHour by remember { mutableStateOf("0.0") }
    var dollarsADay by remember { mutableStateOf("0.0") }
    var accessible by remember { mutableStateOf(true) }
    var sheltered by remember { mutableStateOf(true) }
    var searchedSuggestion = remember { mutableStateOf<Suggestion?>(null) }

    val suggestions by addSpotViewModel.suggestions.collectAsState()
    val query by addSpotViewModel.query.collectAsState()
    val currentLocation by addSpotViewModel.currentLocation.collectAsState()
    val suggestionGenerator = remember { SearchBoxFetcher() }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth().background(color = Color(0xFFF5F5F5))) {
        SpotOnTopBar("Add Spot", onBackClick)

        Column(modifier = Modifier.padding(16.dp)) {

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

                    IconToggleButton(
                        checked = sheltered,
                        onCheckedChange = { newState ->
                            sheltered = newState
                        },
                        content = {
                            if (sheltered) {
                                Image(
                                    modifier = Modifier.size(17.dp),
                                    imageVector = vectorResource(Res.drawable.accessibility_on),
                                    contentDescription = null
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(17.dp),
                                    imageVector = vectorResource(Res.drawable.accessibility_off),
                                    contentDescription = null
                                )
                            }
                        }
                    )

                    IconToggleButton(
                        checked = accessible,
                        onCheckedChange = { newState ->
                            accessible = newState
                        },
                        content = {
                            if (accessible) {
                                Image(
                                    modifier = Modifier.size(17.dp),
                                    imageVector = vectorResource(Res.drawable.weather_on),
                                    contentDescription = null
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(17.dp),
                                    imageVector = vectorResource(Res.drawable.weather_off),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                TextField(
                    label = { Text("Add $/hour") },
                    value = dollarsAnHour,
                    onValueChange = { dollarsAnHour = it },
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
                        .height(55.dp)
                        .weight(0.45f)
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))

                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    label = { Text("Add $/day") },
                    value = dollarsADay,
                    onValueChange = { dollarsADay = it },
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
                        .height(55.dp)
                        .weight(0.45f)
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column() {
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

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
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
                                    searchedSuggestion.value = suggestion
                                },
                            color = Color.DarkGray,
                        )
                    }
                }
            }

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
                    val address = searchedSuggestion.value?.name ?: "dev spot"
                    val spot = Spot("999", "dev3", address, currentLocation.longitude, currentLocation.latitude)
                    scope.launch {
                        addSpotViewModel.createSpot(spot)
                    }
                    onSpotAdded() },
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