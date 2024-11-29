package com.overdrive.cruiser.utils

import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import com.overdrive.cruiser.models.Coordinate


class LocationProvider {

    private val geoLocation = Geolocator.mobile()

    suspend fun getCurrentLocation(): Coordinate? {
        return when (val result = geoLocation.current()) {
            is GeolocatorResult.Success -> {
                val coordinates = result.data.coordinates
                Coordinate(coordinates.latitude, coordinates.longitude)
            }
            is GeolocatorResult.Error -> {
                // Handle error cases here if needed
                null
            }
        }
    }
}
