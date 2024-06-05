package core.network

import features.auth.data.models.User
import features.home.data.models.DeviceDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiService(private val client: HttpClient, private val baseUrl: String) {

    suspend fun userRegister(user: User): HttpResponse {
        return client.post("$baseUrl/api/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun  userLogin(user: User): HttpResponse{
        return  client.post("$baseUrl/api/login"){
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun addDevice(device: DeviceDTO): HttpResponse {
        return client.post("$baseUrl/api/devices") {
            contentType(ContentType.Application.Json)
            setBody(device)
        }
    }
}