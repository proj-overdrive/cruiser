package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import kotlinx.coroutines.flow.StateFlow

// Called from Swift - initializes the MapView
fun setViewFactories(
    mapWithSwiftViewFactory: (
        contentPaddingState: StateFlow<PaddingValues>,
    ) -> MapWithSwiftViewFactory,
) {
    com.overdrive.cruiser.mapWithSwiftViewFactory = mapWithSwiftViewFactory
}