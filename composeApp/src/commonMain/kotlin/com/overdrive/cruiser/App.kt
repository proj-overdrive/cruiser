package com.overdrive.cruiser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.utils.LocationProvider
import com.overdrive.cruiser.views.NavigationBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var currentCoordinates by remember { mutableStateOf(Coordinate.DEFAULT) }

    MaterialTheme {
        val locationProvider = remember { LocationProvider() }
        LaunchedEffect(Unit) {
            Coordinate.initializeDefaultLocation(locationProvider)
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            NavigationBar()
        }
    }
}
