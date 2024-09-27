package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import com.overdrive.cruiser.models.Spot
import kotlinx.coroutines.flow.StateFlow

// Called from Swift - initializes the MapView
fun setViewFactories(
    mapWithSwiftViewFactory: (
        contentPaddingState: StateFlow<PaddingValues>,
        contentSpotsState: StateFlow<List<Spot>>,
    ) -> MapWithSwiftViewFactory,
) {
    com.overdrive.cruiser.mapWithSwiftViewFactory = mapWithSwiftViewFactory
}
