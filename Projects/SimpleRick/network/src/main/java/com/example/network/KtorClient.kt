package com.example.network

import com.example.network.models.remote.RemoteCharacter
import com.example.network.models.remote.toDomainCharacter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import com.example.network.models.domain.Character

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://rickandmortyapi.com/api/") } // base url

        install(Logging) {
            logger = Logger.SIMPLE // logging in console
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

   suspend fun getCharacter(id: Int): Character {
        return client.get("character/$id")
            .body<RemoteCharacter>()
            .toDomainCharacter()
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return 
    }
}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T): ApiOperation<T>
    data class Failure<T>(val exception: Exception): ApiOperation<T>
}
































