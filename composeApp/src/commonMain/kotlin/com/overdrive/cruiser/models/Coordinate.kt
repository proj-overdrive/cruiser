package com.overdrive.cruiser.models

import com.overdrive.cruiser.utils.LocationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        var DEFAULT = Coordinate(-123.3656, 25.4284)

        fun initializeDefaultLocation(locationProvider: LocationProvider) {
            CoroutineScope(Dispatchers.Main).launch {
                val location = locationProvider.getCurrentLocation()
                location?.let { userLocation ->
                    DEFAULT = userLocation
                    MapViewModel.updateCurrentLocation(userLocation)
                } ?: run {
                    println("Failed to get current location.")
                }
            }
        }
    }
}
