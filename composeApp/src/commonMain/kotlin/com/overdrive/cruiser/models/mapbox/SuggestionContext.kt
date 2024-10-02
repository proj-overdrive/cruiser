package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionContext (
    @SerialName("country") val country: Country? = null,
    @SerialName("region") val region: Region? = null,
    @SerialName("postcode") val postcode: Postcode? = null,
    @SerialName("district") val district: District? = null,
    @SerialName("place") val place: Place? = null,
    @SerialName("locality") val locality: Locality? = null,
    @SerialName("neighborhood") val neighborhood: Neighborhood? = null,
    @SerialName("address") val address: Address? = null,
    @SerialName("street") val street: Street? = null,
)
