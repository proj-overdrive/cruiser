package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Region(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("region_code") val regionCode: String? = null,
    @SerialName("region_code_full") val regionCodeFull: String? = null,
)
