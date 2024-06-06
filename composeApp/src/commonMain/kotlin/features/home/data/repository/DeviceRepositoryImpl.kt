package features.home.data.repository

import core.network.ApiService
import core.utils.imageConverter.ImageStorage
import features.home.data.mappers.toDeviceDTO
import features.home.domain.entities.Device
import features.home.domain.repository.DeviceRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

class DeviceRepositoryImpl(private val apiClient: ApiService, private val imageStorage: ImageStorage) :
    DeviceRepository {
    override suspend fun addDevice(device: Device) {

        val response: HttpResponse = apiClient.addDevice(device.toDeviceDTO(imageStorage))

        return when (response.status.value) {
            200 -> {

                Unit
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