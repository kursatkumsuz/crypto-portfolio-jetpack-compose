package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.kursatkumsuz.managecryptoportfolio.data.remote.ApiService
import com.kursatkumsuz.managecryptoportfolio.domain.model.CoinResponse
import com.kursatkumsuz.managecryptoportfolio.domain.repository.CoinRepository

class CoinRepositoryImp (private val apiService: ApiService): CoinRepository {

    override suspend fun getCoinList(): CoinResponse {
        return apiService.getCoinList()
    }
}