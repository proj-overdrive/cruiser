package com.overdrive.cruiser.models

import com.overdrive.cruiser.endpoints.SpotFetcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MySpotsViewModel {
    private val _spots = MutableStateFlow(emptyList<Spot>())
    val spots: StateFlow<List<Spot>> = _spots

    suspend fun fetchSpotsByOwnerId(ownerId: String) {
        val spots = SpotFetcher().fetchByOwnerId(ownerId)
        _spots.value = spots
    }

    fun updateFakeSpots() {
        val spot = Spot(
            id = "1",
            ownerId = "alyssa",
            address = "123 Main St",
            latitude = 37.7749,
            longitude = -122.4194,
        )
        _spots.value = listOf(spot)
    }

    suspend fun updateSpots() {
        _spots.value = SpotFetcher().fetch()
    }
}