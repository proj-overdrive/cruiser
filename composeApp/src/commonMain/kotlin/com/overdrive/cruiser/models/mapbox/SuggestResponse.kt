package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestResponse(
    @SerialName("suggestions") val suggestions: List<Suggestion>
)
