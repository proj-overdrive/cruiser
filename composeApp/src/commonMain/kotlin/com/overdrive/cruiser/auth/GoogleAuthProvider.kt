package com.overdrive.cruiser.auth

import androidx.compose.runtime.Composable

interface GoogleAuthProvider {
    @Composable
    fun getUiProvider(): GoogleAuthUiProvider

    suspend fun signOut()
}

@Composable
expect fun getGoogleAuthProvider(): GoogleAuthProvider
