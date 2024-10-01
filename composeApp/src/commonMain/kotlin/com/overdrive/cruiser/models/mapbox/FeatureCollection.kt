package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureCollection(
    @SerialName("type") val type: String,
    @SerialName("features") val features: List<Feature>,
)
