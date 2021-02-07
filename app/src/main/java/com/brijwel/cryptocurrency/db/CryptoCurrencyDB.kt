package com.brijwel.cryptocurrency.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brijwel.cryptocurrency.model.CryptoCurrency

/**
 * Created by Brijwel on 06-02-2021.
 */
@Database(entities = [CryptoCurrency::class], version = 3, exportSchema = false)
@TypeConverters(DecimalConverter::class)
abstract class CryptoCurrencyDB : RoomDatabase() {
    abstract fun cryptoCurrencyDao(): CryptoCurrencyDao

    companion object {
        private const val DATABASE_NAME = "crypto_currency_db"

        @Volatile
        private var INSTANCE: CryptoCurrencyDB? = null

        fun getDB(application: Application): CryptoCurrencyDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    application,
                    CryptoCurrencyDB::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}