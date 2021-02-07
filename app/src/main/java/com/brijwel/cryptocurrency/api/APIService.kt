package com.brijwel.cryptocurrency.api

import com.brijwel.cryptocurrency.model.CryptoDataHolder
import retrofit2.http.GET

/**
 * Created by Brijwel on 06-02-2021.
 */
interface APIService {

    @GET("assets")
    suspend fun getCryptoData(): CryptoDataHolder

}