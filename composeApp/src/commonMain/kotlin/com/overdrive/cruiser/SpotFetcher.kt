package com.overdrive.cruiser

import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.utils.requestGet

class SpotFetcher {
    suspend fun fetch(): List<Spot> {
        val spots: List<Spot> = fetchSpots()
        return spots
    }

    private suspend fun fetchSpots(): List<Spot> {
        val endpoint = "/spots"
        return requestGet(endpoint)
    }
}