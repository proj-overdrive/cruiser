package com.overdrive.cruiser.auth

import androidx.compose.runtime.Composable

@Composable
actual fun getGoogleAuthProvider(): GoogleAuthProvider = throw UnsupportedOperationException("Not supported on wasm")
