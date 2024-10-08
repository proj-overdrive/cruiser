package com.overdrive.cruiser.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.overdrive.cruiser.models.MapViewModel

@Composable
fun SpotExplorerView(mapViewModel: MapViewModel) {
    val selectedSpot by mapViewModel.selectedSpot.collectAsState()

    Box {
        LaunchedEffect(selectedSpot){
            mapViewModel.updateSpots()
        }

        // Always keep MapView in the background
        MapView(mapViewModel = mapViewModel)

        // Show SpotDetailView on top of MapView when a spot is selected
        AnimatedContent(
            targetState = selectedSpot,
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) + fadeOut() using
                        SizeTransform(clip = false)
            }
        ) { spot ->
            if (spot != null) {
                SpotDetailView(
                    spot = spot,
                    onBack = { mapViewModel.updateSelectedSpot(null) }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color.Transparent))
            }
        }
    }
}
