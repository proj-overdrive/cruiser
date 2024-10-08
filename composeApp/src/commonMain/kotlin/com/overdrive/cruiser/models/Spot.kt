package com.overdrive.cruiser.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Spot(
    @SerialName("id") val id: String,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("address") val address: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    var isBooked: Boolean = false,
)
