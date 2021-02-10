package com.brijwel.cryptocurrency.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.brijwel.cryptocurrency.db.CryptoCurrencyDB
import com.brijwel.cryptocurrency.repo.CryptoCurrencyRepository
import com.brijwel.cryptocurrency.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Brijwel on 06-02-2021.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val repository =
        CryptoCurrencyRepository(CryptoCurrencyDB.getDB(application).cryptoCurrencyDao())
    private val searchQueryLiveData = MutableLiveData("")

    val cryptoCurrencyLiveData = Transformations.switchMap(searchQueryLiveData) {
        if (it.isNullOrEmpty()) repository.getCryptoCurrencies() else repository.getCryptoCurrencies(it)
    }

    val globalMarketCapLiveData = repository.getGlobalMarketCap()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getCryptoCurrencyData(searchQuery: String = "") {
        searchQueryLiveData.value = searchQuery
    }

    private var job = Job()

    fun loadCryptoCurrencyData(isRefresh: Boolean = false) {
        job.cancel()
        viewModelScope.launch(Dispatchers.IO + job) {
            _isLoading.postValue(cryptoCurrencyLiveData.value.isNullOrEmpty() || isRefresh)
            try {
                val cryptoDataHolder = repository.getCryptoData()
                repository.insert(cryptoDataHolder.cryptoData)
                val ids = mutableListOf<String>()
                cryptoDataHolder.cryptoData.forEach { ids.add(it.id) }
                repository.delete(ids)
                Log.d(TAG, "loadCryptoCurrencyData: Success $cryptoDataHolder")
            } catch (e: HttpException) {
                _errorMessage.postValue("Something went wrong!")
                Log.d(TAG, "loadCryptoCurrencyData: Failure ${e.message}")
            } catch (e: IOException) {
                _errorMessage.postValue("No internet connection!")
                Log.d(TAG, "loadCryptoCurrencyData: Failure ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}