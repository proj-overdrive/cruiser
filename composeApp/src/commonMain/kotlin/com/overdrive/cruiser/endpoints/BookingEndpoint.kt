package com.overdrive.cruiser.endpoints

import com.overdrive.cruiser.models.Booking
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.utils.requestGet
import com.overdrive.cruiser.utils.requestPost

class BookingEndpoint {
    suspend fun fetch(): List<Booking> {
        try {
            val endpoint = "/bookings"
            return requestGet(endpoint)
        } catch (e : Exception) {
            return listOf()
        }
    }

    suspend fun fetch(spot: Spot): List<Booking> {
        try {
            val endpoint = "/bookings/${spot.id}"
            return requestGet(endpoint)
        } catch (e : Exception) {
            return listOf()
        }
    }

    suspend fun create(booking: Booking): Booking {
        val endpoint = "/bookings"
        return requestPost(endpoint, booking)
    }
}
