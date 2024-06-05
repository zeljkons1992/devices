package core.network

import core.utils.datastore.DataStoreRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun provideHttpClient(dataStoreRepository: DataStoreRepository): HttpClient {
    return HttpClient(CIO) {
        install(DefaultRequest) {
            val token = dataStoreRepository.getString("jwtToken")
            if (token?.isNotEmpty() == true) {
                header("x-auth-token", token)
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}