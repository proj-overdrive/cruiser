package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.views.MapWithSwiftViewFactory
import kotlinx.coroutines.flow.StateFlow

// Called from Swift - initializes the MapView
fun setViewFactories(
    mapWithSwiftViewFactory: (
        contentPaddingState: StateFlow<PaddingValues>,
        contentLocationState: StateFlow<Coordinate>,
        contentSpotsState: StateFlow<List<Spot>>,
        onSpotSelected: (Spot) -> Unit
    ) -> MapWithSwiftViewFactory,
) {
    com.overdrive.cruiser.views.mapWithSwiftViewFactory = mapWithSwiftViewFactory
}
