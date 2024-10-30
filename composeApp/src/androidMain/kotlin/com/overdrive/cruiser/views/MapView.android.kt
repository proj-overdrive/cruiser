package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultAttributionSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultCompassSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultLogoSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultScaleBarSettings
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotationGroup
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A composable that displays a map with a markers at the given location.
 *
 * @param modifier The modifier to apply to this layout.
 * @param contentPadding The padding to apply to the map content.
 * @param location The location to center the map on, which will live update.
 * @param spots The spots to display on the map.
 */
@OptIn(MapboxExperimental::class)
@Composable
actual fun SpotMapView(
    modifier: Modifier,
    contentPadding: PaddingValues,
    location: Coordinate,
    spots: List<Spot>,
    onSpotSelected: (Spot) -> Unit
)  = with(LocalDensity.current) {
    val context = LocalContext.current

    val logoSettings = remember(contentPadding) {
        defaultLogoSettings(context).toBuilder().apply {
            setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
            setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
            setMarginLeft(marginLeft)
            setMarginRight(marginRight)
        }.build()
    }

    val scaleBarSettings = remember(contentPadding) {
        defaultScaleBarSettings(context).toBuilder().apply {
            setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
            setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
            setMarginLeft(marginLeft)
            setMarginRight(marginRight)
        }.build()
    }

    val attributionSettings = remember(contentPadding) {
        defaultAttributionSettings(context).toBuilder().apply {
            setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
            setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
            setMarginLeft(marginLeft)
            setMarginRight(marginRight)
        }.build()
    }

    val compassSettings = remember(contentPadding) {
        defaultCompassSettings(context).toBuilder().apply {
            setMarginTop(marginTop + contentPadding.calculateTopPadding().toPx())
            setMarginBottom(marginBottom + contentPadding.calculateBottomPadding().toPx())
        }.build()
    }

    val mapViewportState = remember { MapViewportState() }

    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        logoSettings = logoSettings,
        scaleBarSettings = scaleBarSettings,
        attributionSettings = attributionSettings,
        compassSettings = compassSettings,
    ) {
        CircleAnnotation(
            point = Point.fromLngLat(location.latitude, location.longitude),
            circleColorString = "#007BFF",
            circleOpacity = 0.7,
            circleRadius = 15.0,
        )
        CircleAnnotationGroup(
            annotations = spots.map {
                CircleAnnotationOptions()
                    .withPoint(Point.fromLngLat(it.longitude, it.latitude))
                    .withCircleColor(if (it.isBooked) "#FF5733" else "#28A745")
                    .withCircleOpacity(0.7)
                    .withCircleRadius(10.0)
            },
            onClick = { circleAnnotation ->
                spots.firstOrNull {
                    it.latitude == circleAnnotation.point.latitude() &&
                            it.longitude == circleAnnotation.point.longitude()
                }?.let { spot ->
                    onSpotSelected(spot)
                }
                true
            },
        )
    }

    LaunchedEffect(location) {
        mapViewportState.easeTo(
            com.mapbox.maps.CameraOptions.Builder()
                .center(Point.fromLngLat(location.latitude, location.longitude))
                .zoom(13.0)
                .build()
        )
    }
}
