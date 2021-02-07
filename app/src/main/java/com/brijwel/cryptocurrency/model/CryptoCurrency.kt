package com.brijwel.cryptocurrency.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

/**
 * Created by Brijwel on 06-02-2021.
 */
@Entity
data class CryptoCurrency(
    @PrimaryKey
    val id: String,
    val rank: Int,
    val symbol: String,
    val name: String,
    val marketCapUsd: BigDecimal,
    val priceUsd: BigDecimal,
    val changePercent24Hr: Float,
    val explorer: String
) {
    val logoUrl: String get() = """https://static.coincap.io/assets/icons/${symbol.toLowerCase(Locale.ROOT)}@2x.png"""
}