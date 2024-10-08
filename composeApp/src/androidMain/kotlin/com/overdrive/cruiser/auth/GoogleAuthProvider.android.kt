package com.overdrive.cruiser.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager

class GoogleAuthProviderImpl(
    private val credentials: GoogleAuthCredentials,
    private val credentialManager: CredentialManager,
): GoogleAuthProvider {
    @Composable
    override fun getUiProvider(): GoogleAuthUiProvider {
        val activityContext = LocalContext.current
        return GoogleAuthUiProviderImpl(
            activityContext = activityContext,
            credentialManager = credentialManager,
            credentials = credentials
        )
    }

    override suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}

@Composable
actual fun getGoogleAuthProvider(): GoogleAuthProvider {
    val context = LocalContext.current
    val credentials = GoogleAuthCredentials(
        serverId = "48643618736-o7920fcp7si1at4kl6b9pkl9dq3cbe93.apps.googleusercontent.com"
    )
    val credentialManager = CredentialManager.create(context)
    return GoogleAuthProviderImpl(
        credentials = credentials,
        credentialManager = credentialManager
    )
}
