package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Neighborhood(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
)
