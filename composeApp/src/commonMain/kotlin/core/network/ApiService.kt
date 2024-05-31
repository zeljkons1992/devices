package core.network

import features.auth.data.models.JwtToken
import features.auth.data.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ApiService: KoinComponent{
    private val client: HttpClient by inject()

    suspend fun userRegister(user: User) : JwtToken{
        val response = client.post("/api/register"){
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        return  response.body()
    }
}