package com.kursatkumsuz.managecryptoportfolio.data.remote

import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {

    @Headers("X-CMC_PRO_API_KEY: e4265187-ade2-4eb7-b400-a0f08befe3d4")
    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinList() : CoinResponse

}