package features.home.data.repository

import core.network.ApiService
import features.home.data.models.toDataModel
import features.home.domain.entities.DeviceEntity
import features.home.domain.repository.DeviceRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

class DeviceRepositoryImpl(private val apiClient: ApiService) :
    DeviceRepository {
    override suspend fun addDevice(device: DeviceEntity) {
        val response: HttpResponse = apiClient.addDevice(device.toDataModel())
        println("evoooooo")
        println(response.status)
        println(response.body<Any?>().toString())
        return when (response.status.value) {
            200 -> {

            }
            400, 409 -> {
                val errorResponse: ErrorResponse = response.body()
                throw DeviceException(errorResponse.msg)
            }
            else -> {
                throw DeviceException("Registration failed with status code: ${response.status.value}")
            }
        }
    }
}

@Serializable
data class ErrorResponse(val msg: String)

class DeviceException(message: String) : Exception(message)