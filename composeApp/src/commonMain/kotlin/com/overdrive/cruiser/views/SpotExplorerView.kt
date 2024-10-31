package com.overdrive.cruiser.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.overdrive.cruiser.models.MapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotExplorerView(mapViewModel: MapViewModel) {
    val scope = rememberCoroutineScope()
    val selectedSpot by mapViewModel.selectedSpot.collectAsState()
    val showFiltering by mapViewModel.showFiltering.collectAsState()
    val datePickerState = rememberDatePickerState()

    Box {
        LaunchedEffect(selectedSpot){
            mapViewModel.updateSpots()
        }

        // Always keep MapView in the background
        MapView(mapViewModel = mapViewModel)

        // Overlay for intercepting touches
        if (selectedSpot != null || showFiltering) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable(
                        enabled = true,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
            )
        }

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
                    onBack = {
                        mapViewModel.updateSelectedSpot(null)
                        scope.launch {
                            mapViewModel.updateSpots()
                        }
                    }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color.Transparent))
            }
        }

        AnimatedContent(
            targetState = showFiltering,
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) + fadeOut() using
                        SizeTransform(clip = false)
            }
        ) { showFiltering ->
            if (showFiltering) {
                DatePickerView(
                    state = datePickerState,
                    onBack = {
                        mapViewModel.setShowFiltering(false)
                        scope.launch {
                            mapViewModel.updateSpots()
                        }
                    },
                    onSelected = {
                        mapViewModel.updateTimeRange(it)
                    }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color.Transparent))
            }
        }
    }
}
