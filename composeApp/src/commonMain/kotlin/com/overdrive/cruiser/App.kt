package com.overdrive.cruiser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.overdrive.cruiser.views.NavigationBar
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val geoLocation = remember { Geolocator.mobile() }

        LaunchedEffect(Unit) {
            when (val result = geoLocation.current()) {
                is GeolocatorResult.Success -> {
                    println("LOCATION: ${result.data.coordinates}")
                }
                is GeolocatorResult.Error -> when (result) {
                    is GeolocatorResult.NotSupported -> println("LOCATION ERROR: ${result.message}")
                    is GeolocatorResult.NotFound -> println("LOCATION ERROR: ${result.message}")
                    is GeolocatorResult.PermissionError -> println("LOCATION ERROR: ${result.message}")
                    is GeolocatorResult.GeolocationFailed -> println("LOCATION ERROR: ${result.message}")
                    else -> println("LOCATION ERROR: ${result.message}")
                }
            }
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            NavigationBar()
        }
    }
}
