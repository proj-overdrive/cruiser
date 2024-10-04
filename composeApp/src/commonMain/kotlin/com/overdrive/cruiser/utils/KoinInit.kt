package com.overdrive.cruiser.utils

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    includes(platformCoreModule)
}

/**
 * Initializes Koin, called from Swift
 */
fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

internal expect val platformCoreModule: Module
