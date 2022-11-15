package com.kursatkumsuz.managecryptoportfolio.domain.usecase.portfolio

import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository
import com.kursatkumsuz.managecryptoportfolio.domain.repository.PortfolioRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
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
            emit(Response.Success(portfolioRepository.getPortfolio(authRepository.userUid()).await()))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}