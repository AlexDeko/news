package com.news.client

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType

object Api {
    const val url =
        "https://"


    val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = mutableListOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }
}