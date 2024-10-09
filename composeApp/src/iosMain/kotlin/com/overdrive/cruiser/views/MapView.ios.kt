package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.UIKit.UIViewController

/**
 * A composable that displays a map with a markers at the given location.
 *
 * @param modifier The modifier to apply to this layout.
 * @param contentPadding The padding to apply to the map content.
 * @param location The location to center the map on, which will live update.
 * @param spots The spots to display on the map.
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SpotMapView(
    modifier: Modifier,
    contentPadding: PaddingValues,
    location: Coordinate,
    spots: List<Spot>,
    onSpotSelected: (Spot) -> Unit
) {
    val contentPaddingState = remember { MutableStateFlow(contentPadding) }
    val contentLocationState = remember { MutableStateFlow(location) }
    val contentSpotsState = remember { MutableStateFlow(spots) }
    val contentOnSpotSelected = remember { onSpotSelected }

    val factory = remember {
        mapWithSwiftViewFactory(contentPaddingState, contentLocationState, contentSpotsState, contentOnSpotSelected)
    }

    UIKitViewController(
        factory = { factory.viewController },
        update = {
            contentPaddingState.value = contentPadding
            contentLocationState.value = location
            contentSpotsState.value = spots
        },
        modifier = modifier,
    )
}

internal lateinit var mapWithSwiftViewFactory: (
    contentPaddingState: StateFlow<PaddingValues>,
    contentLocationState: StateFlow<Coordinate>,
    contentSpotsState: StateFlow<List<Spot>>,
    onSpotSelected: (Spot) -> Unit
) -> MapWithSwiftViewFactory

interface MapWithSwiftViewFactory {
    val viewController: UIViewController
}
