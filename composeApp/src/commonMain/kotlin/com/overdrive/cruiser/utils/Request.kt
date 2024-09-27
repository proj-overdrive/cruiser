package com.overdrive.cruiser.utils

import com.overdrive.cruiser.getPlatformBasicName
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Temporary logic while working with localhost api
val BASE_URL: String =
    when (getPlatformBasicName()) {
        "iOS" -> "http://localhost:8080"
        "Android" -> "http://10.0.2.2:8080"
        else -> throw Error("INVALID PLATFORM: unable to determine API base")
    }

val httpClient = HttpClient {
    install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
    }
}

suspend inline fun <reified T> requestGet(endpoint: String): T {
    val response: HttpResponse = httpClient.get(BASE_URL + endpoint)
    return response.body()
}