package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.AddSpotViewModel
import com.overdrive.cruiser.models.MapViewModel
import com.overdrive.cruiser.models.MySpotsViewModel
import com.overdrive.cruiser.models.SavedSpotsViewModel
import com.overdrive.cruiser.models.UserViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.my_spots
import cruiser.composeapp.generated.resources.saved_spots
import org.jetbrains.compose.resources.vectorResource

/**
 * The different screens that can be displayed in the app.
 */
enum class Screen {
    Map, User, MySpots, SavedSpots, AddSpot
}

/**
 * A composable that displays the app's navigation bar.
 *
 * @param spots The spots to display on the map.
 */
@Composable
fun NavigationBar() {
    var selectedScreen by remember { mutableStateOf(Screen.Map) }
    val mapViewModel = remember { MapViewModel() }
    val userViewModel = remember { UserViewModel() }
    val mySpotsViewModel = remember { MySpotsViewModel() }
    val savedSpotsViewModel = remember { SavedSpotsViewModel() }

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
                    icon = {
                        Image(
                            modifier = Modifier.size(20.dp),
                            imageVector = vectorResource(Res.drawable.my_spots),
                            contentDescription = null
                        )},
                    label = { Text("My Spots") },
                    selected = selectedScreen == Screen.MySpots,
                    onClick = { selectedScreen = Screen.MySpots }
                )
                BottomNavigationItem(
                    icon = {
                        Image(
                            modifier = Modifier.size(20.dp),
                            imageVector = vectorResource(Res.drawable.saved_spots),
                            contentDescription = null
                        )},
                    label = { Text("Saved Spots") },
                    selected = selectedScreen == Screen.SavedSpots,
                    onClick = { selectedScreen = Screen.SavedSpots }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("User") },
                    selected = selectedScreen == Screen.User,
                    onClick = { selectedScreen = Screen.User }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (selectedScreen) {
                Screen.Map -> MapView(mapViewModel)
                Screen.User -> UserView(userViewModel)
                Screen.MySpots -> MySpotsView(mySpotsViewModel) {
                    selectedScreen = Screen.AddSpot
                }
                Screen.SavedSpots -> SavedSpotsView(savedSpotsViewModel)
                Screen.AddSpot -> AddSpotView(
                    onBackClick = { selectedScreen = Screen.MySpots },
                    addSpotViewModel = AddSpotViewModel()
                )
            }
        }
    }
}
