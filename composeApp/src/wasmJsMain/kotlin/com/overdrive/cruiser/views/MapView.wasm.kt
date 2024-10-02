package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
actual fun SpotMapView(
    modifier: Modifier,
    contentPadding: PaddingValues,
    location: Coordinate,
    spots: List<Spot>
) {
    // TODO: Implement
}
