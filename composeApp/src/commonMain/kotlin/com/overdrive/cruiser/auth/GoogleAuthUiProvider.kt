package com.overdrive.cruiser.auth

interface GoogleAuthUiProvider {
    /**
     * Opens Sign In with Google UI,
     * @return returns GoogleUser
     */
    suspend fun signIn(): GoogleUser?
}
