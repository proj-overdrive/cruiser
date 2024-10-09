package com.overdrive.cruiser.auth

import androidx.compose.runtime.Composable
import cocoapods.GoogleSignIn.GIDSignIn
import kotlinx.cinterop.ExperimentalForeignApi

class GoogleAuthProviderImpl: GoogleAuthProvider {
    @Composable
    override fun getUiProvider(): GoogleAuthUiProvider = GoogleAuthUiProviderImpl()

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun signOut() {
        GIDSignIn.sharedInstance.signOut()
    }
}

@Composable
actual fun getGoogleAuthProvider(): GoogleAuthProvider = GoogleAuthProviderImpl()
