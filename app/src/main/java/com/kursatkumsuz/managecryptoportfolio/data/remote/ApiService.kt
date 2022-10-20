package com.kursatkumsuz.managecryptoportfolio.data.remote

import com.kursatkumsuz.managecryptoportfolio.domain.model.CoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {

    @Headers("YOUR_KEY")
    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinList() : CoinResponse
}