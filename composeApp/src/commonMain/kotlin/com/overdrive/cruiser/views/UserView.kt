package com.overdrive.cruiser.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.UserViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.clock
import cruiser.composeapp.generated.resources.email
import cruiser.composeapp.generated.resources.location
import cruiser.composeapp.generated.resources.notification
import cruiser.composeapp.generated.resources.payment
import cruiser.composeapp.generated.resources.phone
import cruiser.composeapp.generated.resources.profile_picture
import cruiser.composeapp.generated.resources.report
import cruiser.composeapp.generated.resources.security
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

enum class UserScreen {
    UserSettings, RentalStatistics
}

@Composable
fun UserView(userViewModel: UserViewModel, userType: String, userScreen: UserScreen, onLogOut: () -> Unit,
             onResetUserScreen: () -> Unit) {
    var selectedScreen by remember { mutableStateOf(userScreen) }

    AnimatedContent(
        targetState = selectedScreen,
        transitionSpec = {
            if (targetState == UserScreen.UserSettings) {
                slideInHorizontally(initialOffsetX = { -it }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { it }) + fadeOut() using
                        SizeTransform(clip = false)
            } else {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn() togetherWith
                        slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() using
                        SizeTransform(clip = false)
            }
        }
    ) { selected ->
        when (selected) {
            UserScreen.UserSettings -> UserSettingsView(userType, onLogOut) { selectedScreen = it; }
            UserScreen.RentalStatistics -> RentalStatisticsView(
                {selectedScreen = UserScreen.UserSettings},
                onResetUserScreen
            )
        }
    }
}

@Composable
fun UserSettingsView(userType: String, onLogOut: () -> Unit, onNavigate: (UserScreen) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F5F5))
            .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                imageVector = vectorResource(Res.drawable.profile_picture),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    Modifier.padding(bottom = 64.dp)
                ) {
                    item {
                        Text("Preferences")
                    }
                    item {
                        ProfileButton(
                            "Notifications",
                            Res.drawable.notification,
                            onClick = { /*TODO*/ }
                        )
                    }
                    item {
                        ProfileButton(
                            "Personal Information",
                            Res.drawable.email,
                            onClick = { /*TODO*/ }
                        )
                    }
                    item {
                        ProfileButton(
                            "Login & Security",
                            Res.drawable.phone,
                            onClick = { /*TODO*/ }
                        )
                    }
                    item {
                        ProfileButton(
                            if (userType == "Owner") "Rental Statistics" else "Rental History",
                            Res.drawable.clock,
                            onClick = { onNavigate(UserScreen.RentalStatistics) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Text("Billing")
                    }
                    item {
                        ProfileButton(
                            "Payment Methods",
                            Res.drawable.payment,
                            onClick = { /*TODO*/ }
                        )
                    }
                    item {
                        ProfileButton(
                            "Billing Address",
                            Res.drawable.location,
                            onClick = { /*TODO*/ }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Text("Other")
                    }
                    item {
                        ProfileButton(
                            "Report a Problem",
                            Res.drawable.report,
                            onClick = { /*TODO*/ }
                        )
                    }
                    item {
                        ProfileButton(
                            "Security Policy",
                            Res.drawable.security,
                            onClick = { /*TODO*/ }
                        )
                    }
                }

                Button(
                    onClick = { onLogOut() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF9784B)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(2.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Log Out",
                        modifier = Modifier.padding(0.dp, 4.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileButton(text: String, icon: DrawableResource, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(2.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                imageVector = vectorResource(icon),
                contentDescription = null
            )
            Text(text, modifier = Modifier.padding(4.dp, 0.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Outlined.ArrowForward,
                modifier = Modifier.size(24.dp).padding(4.dp),
                contentDescription = null
            )
        }
    }
}