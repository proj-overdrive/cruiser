package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Suggestion(
    @SerialName("name") val name: String,
    @SerialName("name_preferred") val namePreferred: String? = null,
    @SerialName("mapbox_id") val mapboxId: String,
    @SerialName("feature_type") val featureType: String,
    @SerialName("address") val address: String? = null,
    @SerialName("full_address") val fullAddress: String? = null,
    @SerialName("place_formatted") val placeFormatted: String,
    @SerialName("context") val context: SuggestionContext,
    @SerialName("language") val language: String,
    @SerialName("maki") val maki: String? = null,
    @SerialName("poi_category") val poiCategory: List<String>? = null,
    @SerialName("poi_category_ids") val poiCategoryIds: List<String>? = null,
    @SerialName("brand") val brand: List<String>? = null,
    @SerialName("brand_id") val brandId: List<String>? = null,
    @SerialName("external_ids") val externalIds: Map<String, String>? = null,
    @SerialName("metadata") val metadata: Map<String, @Contextual Any>? = null,
    @SerialName("distance") val distance: Double? = null,
    @SerialName("eta") val eta: Double? = null,
    @SerialName("added_distance") val addedDistance: Double? = null,
    @SerialName("added_time") val addedTime: Double? = null,
)
