package com.overdrive.cruiser.utils

import androidx.credentials.CredentialManager
import com.overdrive.cruiser.auth.GoogleAuthCredentials
import com.overdrive.cruiser.auth.GoogleAuthProvider
import com.overdrive.cruiser.auth.GoogleAuthProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformCoreModule: Module = module {
    factory { GoogleAuthCredentials("48643618736-o7920fcp7si1at4kl6b9pkl9dq3cbe93.apps.googleusercontent.com") }
    factory { CredentialManager.create(androidContext()) } bind CredentialManager::class
    singleOf(::GoogleAuthProviderImpl) bind GoogleAuthProvider::class
}
