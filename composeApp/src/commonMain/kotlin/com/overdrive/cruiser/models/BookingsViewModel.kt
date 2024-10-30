package com.overdrive.cruiser.models

import com.overdrive.cruiser.endpoints.BookingEndpoint
import com.overdrive.cruiser.endpoints.SpotFetcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookingsViewModel {
    private val _bookings = MutableStateFlow(emptyList<Booking>())
    val bookings: StateFlow<List<Booking>> = _bookings

    suspend fun updateBookings() {
        _bookings.value = BookingEndpoint().fetch()
    }

    suspend fun getSpotForBooking(spotId: String): Spot {
        return SpotFetcher().fetch(spotId)
    }
}