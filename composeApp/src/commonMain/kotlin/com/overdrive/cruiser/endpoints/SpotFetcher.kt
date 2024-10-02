package com.overdrive.cruiser.endpoints

import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.utils.requestGet

/**
 * Fetches spots from the server.
 */
class SpotFetcher {

    /**
     * Fetches spots from the server.
     */
    suspend fun fetch(): List<Spot> {
        val spots: List<Spot> = fetchSpots()
        return spots
    }

    private suspend fun fetchSpots(): List<Spot> {
        val endpoint = "/spots"
        return requestGet(endpoint)
    }
}
