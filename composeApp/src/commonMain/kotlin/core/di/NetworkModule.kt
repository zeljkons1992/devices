package core.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule()= module{
    HttpClient{
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
            defaultRequest {
                url("https://d2a9-82-117-207-248.ngrok-free.app/")
            }
        }
    }
}