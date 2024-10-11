package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.endpoints.SearchBoxFetcher
import com.overdrive.cruiser.models.Coordinate
import kotlinx.coroutines.launch
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
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
        SearchSuggestionBoxView(Modifier.align(Alignment.TopCenter), mapViewModel)
    }
}
