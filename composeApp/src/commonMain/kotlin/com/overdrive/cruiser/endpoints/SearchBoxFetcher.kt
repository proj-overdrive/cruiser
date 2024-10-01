package com.overdrive.cruiser.endpoints

import com.overdrive.cruiser.models.mapbox.Feature
import com.overdrive.cruiser.models.mapbox.FeatureCollection
import com.overdrive.cruiser.models.mapbox.SuggestResponse
import com.overdrive.cruiser.models.mapbox.Suggestion
import com.overdrive.cruiser.utils.requestGet

/**
 * Fetches search results from the server.
 */
class SearchBoxFetcher(
    // TODO: Remove this token and use the user's session token.
    private val sessionToken: String = "c5a05c49-f64a-4248-8ea0-6a29a1cc47d5",
) {
    private val BASE_URL: String = "https://api.mapbox.com"
    // TODO: Remove this token and instead retrieve from backend.
    private val accessToken = "pk.eyJ1IjoicHJvai1vdmVyZHJpdmUiLCJhIjoiY20xZ20weGQxMDUzaDJtcHo2aTBidHB4biJ9.Wprz5amcVWlYW-rfDG_szA"

    /**
     * Fetches search suggestions from the server.
     *
     * @param query The partial search query.
     */
    suspend fun fetch(query: String): List<Suggestion> {
        val endpoint = "/search/searchbox/v1/suggest?q=$query&access_token=$accessToken&session_token=$sessionToken"
        val searchResults: SuggestResponse = requestGet(endpoint, BASE_URL)
        return searchResults.suggestions
    }

    /**
     * Fetches a feature from the server.
     *
     * @param suggestion The suggestion to fetch.
     */
    suspend fun fetch(suggestion: Suggestion): Feature? {
        val endpoint = "/search/searchbox/v1/retrieve/${suggestion.mapboxId}?access_token=$accessToken&session_token=$sessionToken"
        val feature: FeatureCollection = requestGet(endpoint, BASE_URL)
        return feature.features.firstOrNull()
    }
}
