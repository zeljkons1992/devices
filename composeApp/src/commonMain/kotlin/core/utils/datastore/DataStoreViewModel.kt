package core.utils.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DataStoreViewModel(private val dataStoreRepository: DataStoreRepository) : ViewModel() {

    private val _dataStoreString = MutableStateFlow<String?>(null)
    val dataStoreString: StateFlow<String?> = _dataStoreString

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun saveString(key: String, value: String) {
        dataStoreRepository.saveString(key, value)
    }

    fun getString(key: String) {
        val result = dataStoreRepository.getString(key)
        _dataStoreString.value = result
    }

    fun deleteString(key: String) {
        dataStoreRepository.deleteString(key)
        _dataStoreString.value = null
    }

    fun checkIfLoggedIn() {
        viewModelScope.launch {
            val token = dataStoreRepository.getString("jwtToken")
            _isUserLoggedIn.value = token != null
        }
    }
}