package features.home.data.repository

import core.network.ApiService
import core.utils.imageConverter.ImageStorage
import features.home.data.mappers.toDevice
import features.home.data.mappers.toDeviceDTO
import features.home.data.models.DeviceDTO
import features.home.domain.entities.Device
import features.home.domain.repository.DeviceRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

class DeviceRepositoryImpl(private val apiClient: ApiService, private val imageStorage: ImageStorage) :
    DeviceRepository {
        private lateinit var response: HttpResponse
    override suspend fun addDevice(device: Device) {

        response = apiClient.addDevice(device.toDeviceDTO(imageStorage))

        return when (response.status.value) {
            200 -> {

                Unit
            }
            400, 409 -> {
                val errorResponse: ErrorResponse = response.body()
                throw DeviceException(errorResponse.msg)
            }
            else -> {
                throw DeviceException("Adding devices failed with status code: ${response.status.value}")
            }
        }
    }

    override suspend fun getRemoteDevices(): List<Device> {
        response = apiClient.getDevices()
            return when (response.status.value) {
                200 -> {
                    val deviceList : MutableList<Device> = mutableListOf()
                    response.body<List<DeviceDTO>>().forEach { deviceDTO -> deviceList.add(deviceDTO.toDevice(imageStorage))  }
                    deviceList
                }
                else -> {
                    throw DeviceException("Get all devices failed with status code: ${response.status.value}")
                }
            }
        }

    override suspend fun editDevice(device: Device) {
        print(device)
        response = apiClient.editDevice(device.toDeviceDTO(imageStorage))
        println("!!!!!!!!!!!")
        println(response.status)
        println(response.body<Any?>().toString())
        if (response.status.value != 200) {
            val errorResponse: ErrorResponse = response.body()
            throw DeviceException(errorResponse.msg)
        }
    }

    override suspend fun deleteDevice(device: Device) {
        response = apiClient.deleteDevice(device.toDeviceDTO(imageStorage))
        if (response.status.value != 200) {
            val errorResponse: ErrorResponse = response.body()
            throw DeviceException(errorResponse.msg)
        }
    }
}

@Serializable
data class ErrorResponse(val msg: String)

class DeviceException(message: String) : Exception(message)