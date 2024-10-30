package com.overdrive.cruiser.models

import com.overdrive.cruiser.endpoints.SpotFetcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MySpotsViewModel {
    private val _spots = MutableStateFlow(emptyList<Spot>())
    val spots: StateFlow<List<Spot>> = _spots

    suspend fun updateSpots() {
        _spots.value = SpotFetcher().fetch()
    }

    suspend fun deleteSpot(spot: Spot) {
        SpotFetcher().delete(spot)
    }
}