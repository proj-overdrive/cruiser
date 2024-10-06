package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.overdrive.cruiser.models.Spot

/**
 * The different screens that can be displayed in the app.
 */
enum class Screen {
    Map, User, Settings
}

/**
 * A composable that displays the app's navigation bar.
 *
 * @param spots The spots to display on the map.
 */
@Composable
// TODO: Remove spots as a dependency
fun NavigationBar(spots: List<Spot>) {
    var selectedScreen by remember { mutableStateOf(Screen.Map) }
    val mapViewModel = remember { MapViewModel().apply { updateSpots(spots) } }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    label = { Text("Map") },
                    selected = selectedScreen == Screen.Map,
                    onClick = { selectedScreen = Screen.Map }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("User") },
                    selected = selectedScreen == Screen.User,
                    onClick = { selectedScreen = Screen.User }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = selectedScreen == Screen.Settings,
                    onClick = { selectedScreen = Screen.Settings }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (selectedScreen) {
                Screen.Map -> MapView(mapViewModel = mapViewModel)
                Screen.User -> UserView()
                Screen.Settings -> SettingsView()
            }
        }
    }
}
