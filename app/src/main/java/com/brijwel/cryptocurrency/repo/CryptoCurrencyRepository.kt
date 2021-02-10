package com.brijwel.cryptocurrency.repo

import com.brijwel.cryptocurrency.api.APIClient
import com.brijwel.cryptocurrency.model.CryptoCurrency
import com.brijwel.cryptocurrency.model.CryptoDataHolder
import com.brijwel.cryptocurrency.db.CryptoCurrencyDao

/**
 * Created by Brijwel on 06-02-2021.
 */
class CryptoCurrencyRepository(
    private val currencyDao: CryptoCurrencyDao
) {
    suspend fun getCryptoData(): CryptoDataHolder = APIClient.apiService.getCryptoData()

    suspend fun insert(cryptoCurrencies: List<CryptoCurrency>) = currencyDao.insert(cryptoCurrencies)

    suspend fun delete(ids: List<String>) = currencyDao.delete(ids)

    fun getCryptoCurrencies() = currencyDao.getCryptoCurrencies()

    fun getCryptoCurrencies(searchQuery:String) = currencyDao.getCryptoCurrencies(searchQuery)

    fun getGlobalMarketCap() = currencyDao.getGlobalMarketCap()
}