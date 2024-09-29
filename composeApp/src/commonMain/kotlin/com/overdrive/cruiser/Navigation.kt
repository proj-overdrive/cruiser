package com.overdrive.cruiser

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.overdrive.cruiser.models.Spot


@Composable
fun Navigation(spots: List<Spot>) {
    var selectedScreen by remember { mutableStateOf("Map") }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
                    label = { Text("Map") },
                    selected = selectedScreen == "Map",
                    onClick = { selectedScreen = "Map" }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text("User") },
                    selected = selectedScreen == "User",
                    onClick = { selectedScreen = "User" }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = selectedScreen == "Settings",
                    onClick = { selectedScreen = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (selectedScreen) {
                "Map" -> MapView(spots)
                "User" -> UserView()
                "Settings" -> SettingsView()
            }
        }
    }
}
