package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import com.overdrive.cruiser.models.Spot
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SpotMapView(
    modifier: Modifier,
    contentPadding: PaddingValues,
    spots: List<Spot>,
) {
    val contentPaddingState = remember { MutableStateFlow(contentPadding) }
    val contentSpotsState = remember { MutableStateFlow(spots) }
    val factory = remember { mapWithSwiftViewFactory(contentPaddingState, contentSpotsState) }

    UIKitViewController(
        factory = { factory.viewController },
        update = {
            contentPaddingState.value = contentPadding
            contentSpotsState.value = spots
        },
        modifier = modifier,
    )
}

internal lateinit var mapWithSwiftViewFactory: (
    contentPaddingState: StateFlow<PaddingValues>,
    contentSpotsState: StateFlow<List<Spot>>,
) -> MapWithSwiftViewFactory

interface MapWithSwiftViewFactory {
    val viewController: UIViewController
}
