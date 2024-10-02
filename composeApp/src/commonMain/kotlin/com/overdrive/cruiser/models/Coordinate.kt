package com.overdrive.cruiser.models

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        val DEFAULT = Coordinate(0.0, 0.0)
    }
}
