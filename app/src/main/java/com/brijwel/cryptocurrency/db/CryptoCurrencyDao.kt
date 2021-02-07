package com.brijwel.cryptocurrency.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brijwel.cryptocurrency.model.CryptoCurrency
import java.math.BigDecimal

/**
 * Created by Brijwel on 06-02-2021.
 */
@Dao
interface CryptoCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cryptoCurrencies: List<CryptoCurrency>)

    @Query("DELETE FROM CryptoCurrency WHERE id NOT IN (:ids)")
    suspend fun delete(ids: List<String>)

    @Query("SELECT * FROM CryptoCurrency ORDER BY rank ")
    fun getAllCryptoCurrency(): LiveData<List<CryptoCurrency>>

    @Query("SELECT * FROM CryptoCurrency WHERE name like '%' || :searchQuery || '%' ORDER BY rank ")
    fun getAllCryptoCurrency(searchQuery: String): LiveData<List<CryptoCurrency>>

    @Query("SELECT SUM(marketCapUsd) FROM  CryptoCurrency")
    fun getGlobalMarketCap(): LiveData<BigDecimal?>
}