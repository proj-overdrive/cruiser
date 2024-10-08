package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot

/**
 * A composable that displays a map with a markers at the given location.
 *
 * @param modifier The modifier to apply to this layout.
 * @param contentPadding The padding to apply to the map content.
 * @param location The location to center the map on, which will live update.
 * @param spots The spots to display on the map.
 */
@Composable
expect fun SpotMapView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    location: Coordinate = Coordinate(0.0, 0.0),
    spots: List<Spot> = emptyList(),
    onSpotSelected: (Spot) -> Unit
)
