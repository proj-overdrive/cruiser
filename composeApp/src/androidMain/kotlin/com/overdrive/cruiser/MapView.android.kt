package com.overdrive.cruiser

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultAttributionSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultCompassSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultLogoSettings
import com.mapbox.maps.extension.compose.DefaultSettingsProvider.defaultScaleBarSettings
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

@OptIn(MapboxExperimental::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    contentPadding: PaddingValues,
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

    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = MapViewportState().apply{
            setCameraOptions(
                com.mapbox.maps.CameraOptions.Builder().center(
                    com.mapbox.geojson.Point.fromLngLat(16.3719, 48.2082)
                ).zoom(10.0).build()
            )
        },
        logoSettings = logoSettings,
        scaleBarSettings = scaleBarSettings,
        attributionSettings = attributionSettings,
        compassSettings = compassSettings,
    )
}