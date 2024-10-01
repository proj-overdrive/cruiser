package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("coordinates") val coordinates: List<Double>,
    @SerialName("type") val type: String,
)
