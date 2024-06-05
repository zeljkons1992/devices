package core.utils.datastore


import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class BaseDataStoreRepository(private val settings: Settings) : DataStoreRepository {

    override fun saveString(key: String, value: String) {
        settings[key] = value
    }

    override fun getString(key: String): String? {
        return settings[key]
    }

    override fun deleteString(key: String) {
        settings.remove(key)
    }
}
