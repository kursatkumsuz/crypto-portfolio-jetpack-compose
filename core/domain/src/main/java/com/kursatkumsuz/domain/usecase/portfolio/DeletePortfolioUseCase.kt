package com.kursatkumsuz.domain.usecase.portfolio

import com.kursatkumsuz.domain.repository.AuthRepository
import com.kursatkumsuz.domain.repository.PortfolioRepository
import com.kursatkumsuz.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeletePortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository,
    private val authRepository: AuthRepository
) {
     operator fun invoke(symbol: String): Flow<Response<Void?>> = flow {
        val userUid = authRepository.userUid()
        try {
            emit(Response.Loading)
            emit(Response.Success(portfolioRepository.deletePortfolio(userUid, symbol).await()))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}