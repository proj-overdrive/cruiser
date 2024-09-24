package com.overdrive.cruiser

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getPlatformBasicName(): String
