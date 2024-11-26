package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.views.MapWithSwiftViewFactory
import com.overdrive.cruiser.views.StripePaymentViewFactory
import kotlinx.coroutines.flow.StateFlow

// Called from Swift - initializes view factories for iOS
fun setViewFactories(
    mapWithSwiftViewFactory: (
        contentPaddingState: StateFlow<PaddingValues>,
        contentLocationState: StateFlow<Coordinate>,
        contentSpotsState: StateFlow<List<Spot>>,
        onSpotSelected: (Spot) -> Unit
    ) -> MapWithSwiftViewFactory,
    stripePaymentViewFactory: (
        Long,
        () -> Unit
    ) -> StripePaymentViewFactory
) {
    com.overdrive.cruiser.views.mapWithSwiftViewFactory = mapWithSwiftViewFactory
    com.overdrive.cruiser.views.stripePaymentViewFactory = stripePaymentViewFactory
}
