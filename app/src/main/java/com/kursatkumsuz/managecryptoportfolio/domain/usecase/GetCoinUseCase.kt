package com.kursatkumsuz.managecryptoportfolio.domain.usecase

import com.kursatkumsuz.managecryptoportfolio.domain.model.CoinResponse
import com.kursatkumsuz.managecryptoportfolio.domain.repository.CoinRepository
import com.kursatkumsuz.managecryptoportfolio.util.Resource
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    suspend fun getCoinList(): Resource<CoinResponse> {
       val response = try {
            repository.getCoinList()
        }catch (e: Exception) {
            return Resource.error("Error", null)
        }

        return Resource.success(response)
    }
}