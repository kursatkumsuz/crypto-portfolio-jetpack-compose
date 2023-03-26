package com.kursatkumsuz.domain.repository

import com.kursatkumsuz.domain.model.coin.CoinResponse


interface CoinRepository {

    suspend fun getCoinList() : CoinResponse

}