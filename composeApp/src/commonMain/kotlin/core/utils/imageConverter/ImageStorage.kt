package core.utils.imageConverter

expect class ImageStorage {
    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getImage(fileName:String): ByteArray?
    suspend fun deletedImage(filename:String)
}