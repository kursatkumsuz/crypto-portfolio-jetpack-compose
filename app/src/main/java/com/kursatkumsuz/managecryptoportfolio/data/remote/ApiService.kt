package com.kursatkumsuz.managecryptoportfolio.data.remote

import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {

    @Headers("X-CMC_PRO_API_KEY: YOUR_API_KEY")
    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinList() : CoinResponse

}