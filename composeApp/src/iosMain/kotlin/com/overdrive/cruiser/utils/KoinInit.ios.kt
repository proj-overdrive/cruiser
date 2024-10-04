package com.overdrive.cruiser.utils

import com.overdrive.cruiser.auth.GoogleAuthProvider
import com.overdrive.cruiser.auth.GoogleAuthProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformCoreModule: Module = module {
    singleOf(::GoogleAuthProviderImpl) bind GoogleAuthProvider::class
}
