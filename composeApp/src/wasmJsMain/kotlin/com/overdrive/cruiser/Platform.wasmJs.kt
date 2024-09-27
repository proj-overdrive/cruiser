package com.overdrive.cruiser

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun getPlatformBasicName(): String = "Web"
