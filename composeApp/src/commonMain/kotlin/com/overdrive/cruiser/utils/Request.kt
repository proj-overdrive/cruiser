package com.overdrive.cruiser.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Temporary logic while working with localhost api
const val BASE_URL: String = "http://spoton-env-6.eba-imaqwcfk.us-east-1.elasticbeanstalk.com"

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

suspend inline fun <reified T> requestGet(endpoint: String, baseUrl: String = BASE_URL): T {
    val response: HttpResponse = httpClient.get(baseUrl + endpoint)
    return response.body()
}

suspend inline fun <reified R> requestPost(
    endpoint: String,
    body: R,
    baseUrl: String = BASE_URL
): R {
    val response: HttpResponse = httpClient.post(baseUrl + endpoint) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
    return response.body()
}

suspend inline fun <reified R> requestDelete(
    endpoint: String,
    body: R,
    baseUrl: String = BASE_URL
): HttpStatusCode {
    val response: HttpResponse = httpClient.delete(baseUrl + endpoint) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
    return response.status
}
