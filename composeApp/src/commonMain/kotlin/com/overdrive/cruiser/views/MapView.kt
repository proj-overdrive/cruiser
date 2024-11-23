package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.MapViewModel

/**
 * A composable that displays a map with a search box and suggestions.
 *
 * @param spots The spots to display on the map.
 */
@Composable
fun MapView(mapViewModel: MapViewModel) {
    val currentLocation by mapViewModel.currentLocation.collectAsState()
    val spots by mapViewModel.spots.collectAsState()
    val serverStatus by mapViewModel.offLineServers.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SpotMapView(
            modifier = Modifier.fillMaxSize(),
            location = currentLocation,
            spots = spots,
            onSpotSelected = { spot -> mapViewModel.updateSelectedSpot(spot) }
        )

        LaunchedEffect(Unit) {
            mapViewModel.updateSpots()
        }

        // Search box and suggestion list are overlaid on top of the map
        Column {
            // Search box and suggestion list are overlaid on top of the map
            SearchSuggestionBoxView(modifier = Modifier, mapViewModel)

            if (serverStatus == true) { // because its value is <Boolean?>

                Text(
                    "It appears our servers are offline right now! We are working on it, please hang tight!",
                    modifier = Modifier
                        .background(Color.Red)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    color = Color.White
                )

            }

            Spacer(Modifier.weight(1f))
        }

    }
}
