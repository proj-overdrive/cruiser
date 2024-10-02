package com.overdrive.cruiser.models.mapbox

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureProperties(
    @SerialName("name") val name: String,
    @SerialName("name_preferred") val namePreferred: String? = null,
    @SerialName("mapbox_id") val mapboxId: String,
    @SerialName("feature_type") val featureType: String,
    @SerialName("address") val address: String? = null,
    @SerialName("full_address") val fullAddress: String? = null,
    @SerialName("place_formatted") val placeFormatted: String? = null,
    @SerialName("context") val context: SuggestionContext,
    @SerialName("coordinates") val coordinates: FeaturePropertyCoordinates,
    @SerialName("bbox") val bbox: List<Double>? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("maki") val maki: String? = null,
    @SerialName("poi_category") val poiCategory: List<String>? = null,
    @SerialName("poi_category_ids") val poiCategoryIds: List<String>? = null,
    @SerialName("brand") val brand: List<String>? = null,
    @SerialName("brand_id") val brandId: List<String>? = null,
    @SerialName("external_ids") val externalIds: Map<String, String>? = null,
    @SerialName("metadata") val metadata: Map<String, String>? = null,
)
