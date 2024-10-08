package com.overdrive.cruiser.views

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.overdrive.cruiser.auth.GoogleUser
import com.overdrive.cruiser.auth.getGoogleAuthProvider
import kotlinx.coroutines.launch

interface GoogleButtonUiContainerScope {
    fun onClick()
}

@Composable
fun GoogleButtonUiContainer(
    modifier: Modifier = Modifier,
    onGoogleSignInResult: (GoogleUser?) -> Unit,
    content: @Composable GoogleButtonUiContainerScope.() -> Unit,
) {
    val googleAuthProvider = getGoogleAuthProvider()
    val googleAuthUiProvider = googleAuthProvider.getUiProvider()
    val coroutineScope = rememberCoroutineScope()
    val uiContainerScope = remember {
        object : GoogleButtonUiContainerScope {
            override fun onClick() {
                coroutineScope.launch {
                    val googleUser = googleAuthUiProvider.signIn()
                    onGoogleSignInResult(googleUser)
                }
            }
        }
    }
    Surface(
        modifier = modifier,
        content = { uiContainerScope.content() }
    )
}
