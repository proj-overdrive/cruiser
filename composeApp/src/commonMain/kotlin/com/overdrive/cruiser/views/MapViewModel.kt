package com.overdrive.cruiser.views

import com.overdrive.cruiser.models.Coordinate
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

    fun updateCurrentLocation(location: Coordinate) {
        _currentLocation.value = location
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun updateSuggestions(suggestions: List<Suggestion>) {
        _suggestions.value = suggestions
    }
}
