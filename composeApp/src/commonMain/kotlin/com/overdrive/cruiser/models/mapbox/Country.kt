package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("country_code") val countryCode: String? = null,
    @SerialName("country_code_alpha_3") val countryCodeAlpha3: String? = null,
)
