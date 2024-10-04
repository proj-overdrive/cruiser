package com.overdrive.cruiser.views

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.overdrive.cruiser.auth.GoogleUser

@Composable
fun LoginView() {
    var signedInUser: GoogleUser? by remember { mutableStateOf(null) }

    GoogleButtonUiContainer(onGoogleSignInResult = { googleUser ->
        val idToken= googleUser?.idToken
        // TODO: Use the idToken to authenticate the user
        signedInUser=googleUser
    }) {
        // TODO: Make a look like a Google Sign-In button
        Button(
            onClick = { this.onClick() }
        ) {
            Text("Sign-In with Google")
        }
    }
}