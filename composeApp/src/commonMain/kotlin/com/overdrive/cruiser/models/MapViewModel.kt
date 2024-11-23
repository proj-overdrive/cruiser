package com.overdrive.cruiser.models

import com.overdrive.cruiser.endpoints.BookingEndpoint
import com.overdrive.cruiser.endpoints.SpotFetcher
import com.overdrive.cruiser.models.mapbox.Suggestion
import com.overdrive.cruiser.utils.TimeRange
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

    private val _showFiltering = MutableStateFlow(false)
    val showFiltering: StateFlow<Boolean> = _showFiltering

    private val _timeRange = MutableStateFlow(TimeRange.now())
    val timeRange: StateFlow<TimeRange> = _timeRange

    private val _offlineServers = MutableStateFlow<Boolean?>(null)
    val offLineServers: StateFlow<Boolean?> = _offlineServers

    fun updateCurrentLocation(location: Coordinate) {
        _currentLocation.value = location
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun updateSuggestions(suggestions: List<Suggestion>) {
        _suggestions.value = suggestions
    }

    fun updateSelectedSpot(spot: Spot?) {
        _selectedSpot.value = spot
    }

    fun setShowFiltering(state: Boolean) {
        _showFiltering.value = state
    }

    fun updateTimeRange(range: TimeRange) {
        _timeRange.value = range
    }

    fun updateServerStatus(state: Boolean) {
        _offlineServers.value = state
    }

    suspend fun updateSpots() {
        val fetchedSpots = SpotFetcher().fetch()
        val bookings = BookingEndpoint().fetch()

        updateServerStatus(fetchedSpots.isEmpty() && bookings.isEmpty())

        _spots.value = fetchedSpots.map { spot ->
            val isBooked = bookings.filter {
                    booking -> booking.parkingSpotId == spot.id
            }.any {
                val bookingRange = TimeRange(it.startTime, it.endTime)
                bookingRange.overlap(_timeRange.value)
            }
            spot.copy(isBooked = isBooked)
        }
    }
}

