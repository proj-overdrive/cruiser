package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeaturePropertyCoordinates(
    @SerialName("longitude") val longitude: Double,
    @SerialName("latitude") val latitude: Double,
    @SerialName("accuracy") val accuracy: String? = null,
    @SerialName("routable_points") val routablePoints: List<RoutablePoint>? = null,
)
