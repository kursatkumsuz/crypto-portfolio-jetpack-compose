package com.kursatkumsuz.data.remote

import com.kursatkumsuz.domain.model.coin.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {

    @Headers("X-CMC_PRO_API_KEY: 0200a622-3c07-4844-8fc8-54806b57a13e")
    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinList() : CoinResponse

}