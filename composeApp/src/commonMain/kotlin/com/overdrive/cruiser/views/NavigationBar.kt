package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.AddSpotViewModel
import com.overdrive.cruiser.models.MapViewModel
import com.overdrive.cruiser.models.MySpotsViewModel
import com.overdrive.cruiser.models.BookingsViewModel
import com.overdrive.cruiser.models.UserViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.my_spots
import cruiser.composeapp.generated.resources.saved_spots
import org.jetbrains.compose.resources.vectorResource

/**
 * The different screens that can be displayed in the app.
 */
enum class Screen {
    Map, User, MySpots, Bookings, AddSpot, GetStarted, UserType, Login, Terms
}

/**
 * A composable that displays the app's navigation bar.
 */
@Composable
fun NavigationBar() {
    var selectedScreen by remember { mutableStateOf(Screen.GetStarted) }
    val noNavBarScreens = listOf(Screen.GetStarted, Screen.UserType, Screen.Login, Screen.Terms)
    val mapViewModel = remember { MapViewModel() }
    val userViewModel = remember { UserViewModel() }
    val mySpotsViewModel = remember { MySpotsViewModel() }
    val bookingsViewModel = remember { BookingsViewModel() }
    var userType by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            if (noNavBarScreens.contains(selectedScreen).not()) {
                BottomNavigation(
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        label = { Text("Map") },
                        selected = selectedScreen == Screen.Map,
                        onClick = { selectedScreen = Screen.Map }
                    )
                    if (userType == "Owner") {
                        BottomNavigationItem(
                            icon = {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = vectorResource(Res.drawable.my_spots),
                                    contentDescription = null
                                )
                            },
                            label = { Text("My Spots") },
                            selected = selectedScreen == Screen.MySpots,
                            onClick = { selectedScreen = Screen.MySpots }
                        )
                    } else {
                        BottomNavigationItem(
                            icon = {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = vectorResource(Res.drawable.saved_spots),
                                    contentDescription = null
                                )
                            },
                            label = { Text("Bookings") },
                            selected = selectedScreen == Screen.Bookings,
                            onClick = { selectedScreen = Screen.Bookings }
                        )
                    }
                    BottomNavigationItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("User") },
                        selected = selectedScreen == Screen.User,
                        onClick = { selectedScreen = Screen.User }
                    )
                }
            }
        }
    ) { innerPadding ->
        val bottomPadding = maxOf(innerPadding.calculateBottomPadding() - 10.dp, 0.dp)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding)
        ) {
            when (selectedScreen) {
                Screen.Map -> SpotExplorerView(mapViewModel)
                Screen.User -> UserView(userViewModel, onLogOut = { selectedScreen = Screen.GetStarted })
                Screen.MySpots -> MySpotsView(mySpotsViewModel) {
                    selectedScreen = Screen.AddSpot
                }
                Screen.Bookings -> BookingsView(bookingsViewModel)
                Screen.AddSpot -> AddSpotView(
                    onBackClick = { selectedScreen = Screen.MySpots },
                    onSpotAdded = { selectedScreen = Screen.MySpots },
                    addSpotViewModel = AddSpotViewModel()
                )
                Screen.GetStarted -> GetStartedView { selectedScreen = Screen.UserType }
                Screen.UserType -> UserTypeView({ userType = it; selectedScreen = Screen.Login },
                    { selectedScreen = Screen.GetStarted })
                Screen.Login -> LoginView({ selectedScreen = Screen.Terms },
                    { selectedScreen = Screen.UserType })
                Screen.Terms -> TermsView({ selectedScreen = Screen.Map },
                    { selectedScreen = Screen.Login })
            }
        }
    }
}
