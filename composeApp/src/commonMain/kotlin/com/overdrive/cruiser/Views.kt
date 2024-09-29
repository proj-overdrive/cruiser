package com.overdrive.cruiser

import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.overdrive.cruiser.models.Spot


@Composable
fun UserView() {
    Text("User Screen")
}

@Composable
fun SettingsView() {
    Text("Settings Screen")
}

@Composable
fun MapView(spots: List<Spot>) {
    SpotMapView(
        modifier = Modifier.fillMaxSize(),
        spots = spots,
    )
}