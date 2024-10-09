package com.overdrive.cruiser.endpoints

import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.utils.requestGet
import com.overdrive.cruiser.utils.requestPost

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

    suspend fun fetchByOwnerId(ownerId: String): List<Spot> {
        val endpoint = "/spots/$ownerId"
        return requestGet(endpoint)
    }

    suspend fun create(spot: Spot): Spot {
        val endpoint = "/spots"
        return requestPost(endpoint, spot)
    }

    private suspend fun fetchSpots(): List<Spot> {
        val endpoint = "/spots"
        return requestGet(endpoint)
    }
}
