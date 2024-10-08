package com.overdrive.cruiser.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

enum class BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED
}

@Serializable
data class Booking(
    val id: String,
    val parkingSpotId: String,
    val userId: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    val totalPrice: Double,
    val bookingStatus: BookingStatus,
    val vehicleLicensePlate: String? = null
)

/**
 * Custom serializer for kotlinx.datetime.LocalDateTime.
 */
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString()) // LocalDateTime's toString uses ISO format
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString()) // Parse from ISO format
    }
}
