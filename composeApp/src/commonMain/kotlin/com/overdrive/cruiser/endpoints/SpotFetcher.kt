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
        val endpoint = "/spots"
        return requestGet(endpoint)
    }

    suspend fun fetch(id: String): Spot {
        val endpoint = "/spots/$id"
        return requestGet(endpoint)
    }

    suspend fun create(spot: Spot): Spot {
        val endpoint = "/spots"
        return requestPost(endpoint, spot)
    }
}
