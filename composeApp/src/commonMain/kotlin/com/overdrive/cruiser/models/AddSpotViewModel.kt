package com.overdrive.cruiser.models

import com.overdrive.cruiser.endpoints.SpotFetcher
import com.overdrive.cruiser.models.mapbox.Suggestion
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddSpotViewModel {
    private val _currentLocation = MutableStateFlow(Coordinate.DEFAULT)
    val currentLocation: StateFlow<Coordinate> = _currentLocation

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _suggestions = MutableStateFlow(emptyList<Suggestion>())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions

    private val _spots = MutableStateFlow(emptyList<Spot>())
    val spots: StateFlow<List<Spot>> = _spots

    private val _selectedAddress = MutableStateFlow("dev spot")
    val selectedAddress: StateFlow<String> = _selectedAddress

    fun updateCurrentLocation(location: Coordinate) {
        _currentLocation.value = location
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun updateSuggestions(suggestions: List<Suggestion>) {
        _suggestions.value = suggestions
    }

    fun updateSelectedAddress(address: String) {
        _selectedAddress.value = address
    }

    suspend fun createSpot(spot: Spot) {
        SpotFetcher().create(spot)
    }
}