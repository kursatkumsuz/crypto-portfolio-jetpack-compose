package com.kursatkumsuz.data.repository

import com.kursatkumsuz.data.remote.ApiService

class CoinRepositoryImp(private val apiService: ApiService) :
    com.kursatkumsuz.domain.repository.CoinRepository {

    override suspend fun getCoinList(): com.kursatkumsuz.domain.model.coin.CoinResponse {
        return apiService.getCoinList()
    }

}