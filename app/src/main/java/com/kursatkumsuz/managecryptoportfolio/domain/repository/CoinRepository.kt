package com.kursatkumsuz.managecryptoportfolio.domain.repository

import com.kursatkumsuz.managecryptoportfolio.domain.model.CoinResponse

interface CoinRepository {

    suspend fun getCoinList() : CoinResponse
}