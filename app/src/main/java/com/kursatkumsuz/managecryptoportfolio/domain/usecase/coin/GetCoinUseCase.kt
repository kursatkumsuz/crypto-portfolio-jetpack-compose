package com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin

import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinResponse
import com.kursatkumsuz.managecryptoportfolio.domain.repository.CoinRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(): Flow<Response<CoinResponse>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(repository.getCoinList()))
        }catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}