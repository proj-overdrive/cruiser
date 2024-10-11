package com.overdrive.cruiser.models

import com.overdrive.cruiser.endpoints.BookingEndpoint
import com.overdrive.cruiser.endpoints.SpotFetcher
import com.overdrive.cruiser.models.Booking
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.models.mapbox.Suggestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapViewModel {
    private val _currentLocation = MutableStateFlow(Coordinate.DEFAULT)
    val currentLocation: StateFlow<Coordinate> = _currentLocation

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _suggestions = MutableStateFlow(emptyList<Suggestion>())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions

    private val _spots = MutableStateFlow(emptyList<Spot>())
    val spots: StateFlow<List<Spot>> = _spots

    private val _selectedSpot = MutableStateFlow<Spot?>(null)
    val selectedSpot: StateFlow<Spot?> = _selectedSpot

    private val _bookings = MutableStateFlow(emptyList<Booking>())
    val bookings: StateFlow<List<Booking>> = _bookings

    fun updateCurrentLocation(location: Coordinate) {
        _currentLocation.value = location
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun updateSuggestions(suggestions: List<Suggestion>) {
        _suggestions.value = suggestions
    }

    suspend fun createSpot(spot: Spot) {
        SpotFetcher().create(spot)

    fun updateSelectedSpot(spot: Spot?) {
        _selectedSpot.value = spot
    }

    suspend fun updateSpots() {
        _bookings.value = BookingEndpoint().fetch()
        _spots.value = SpotFetcher().fetch()

        for (spot in _spots.value) {
            spot.isBooked = _bookings.value.any { it.parkingSpotId == spot.id }
        }
    }
}
