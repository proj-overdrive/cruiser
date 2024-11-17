package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.auth.GoogleUser
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.apple_logo
import cruiser.composeapp.generated.resources.facebook_logo
import cruiser.composeapp.generated.resources.google_logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginView(onAuthenticated: () -> Unit) {
    var signedInUser: GoogleUser? by remember { mutableStateOf(null) }

    GoogleButtonUiContainer(onGoogleSignInResult = { googleUser ->
        val idToken = googleUser?.idToken
        // TODO: Use the idToken to authenticate the user
        signedInUser = googleUser

        if (idToken != null) {
            onAuthenticated()
        }
    }) {
        val onGoogleClick = { this.onClick() }

        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            SpotOnLoginBackground(modifier = Modifier.align(Alignment.BottomCenter))

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {

                SpotOnBranding(modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(54.dp))

                SpotOnLoginButton(
                    platform = "Google",
                    onClick = onGoogleClick,
                    logo = Res.drawable.google_logo,
                    iconModifier = Modifier.padding(start = 5.dp).size(50.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SpotOnLoginButton(
                    platform = "Apple",
                    onClick = onGoogleClick,
                    logo = Res.drawable.apple_logo,
                    iconModifier = Modifier.padding(start = 11.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SpotOnLoginButton(
                    platform = "Facebook",
                    onClick = onGoogleClick,
                    logo = Res.drawable.facebook_logo,
                    iconModifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
fun SpotOnLoginButton(platform: String, onClick: () -> Unit, logo: DrawableResource,
                      iconModifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.LightGray,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(logo),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = iconModifier.align(Alignment.CenterStart)
                    .padding(start = 11.dp)
            )

            Text(
                text = "Continue With $platform",
                color = contentColorFor(Color.Black),
            )
        }
    }
}