package com.kursatkumsuz.domain.usecase.portfolio

import com.kursatkumsuz.domain.repository.AuthRepository
import com.kursatkumsuz.domain.repository.PortfolioRepository
import com.kursatkumsuz.util.Response
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke() = flow {
        try {

            emit(Response.Loading)

            val querySnapshot = portfolioRepository.getPortfolio(authRepository.userUid()).await()

            emit(Response.Success(querySnapshot))


        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}