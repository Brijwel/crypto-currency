package com.brijwel.cryptocurrency.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Brijwel on 06-02-2021.
 */
data class CryptoDataHolder(
    @SerializedName("data")
    val cryptoData: List<CryptoCurrency>,
    val timestamp: Long
)