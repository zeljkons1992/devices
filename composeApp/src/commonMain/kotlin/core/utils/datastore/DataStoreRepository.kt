package core.utils.datastore

interface DataStoreRepository {
    fun saveString(key: String, value: String)
    fun getString(key: String): String?
    fun deleteString(key: String)
}