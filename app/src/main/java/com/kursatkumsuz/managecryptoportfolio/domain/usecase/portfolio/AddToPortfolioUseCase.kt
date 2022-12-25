package com.kursatkumsuz.managecryptoportfolio.domain.usecase.portfolio

import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository
import com.kursatkumsuz.managecryptoportfolio.domain.repository.PortfolioRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddToPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository,
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        portfolioModel: PortfolioModel
    ) = flow {
        try {
            emit(Response.Loading)
            emit(
                Response.Success(
                    portfolioRepository.addToPortfolio(
                        authRepository.userUid(),
                        portfolioModel
                    ).await()
                )
            )
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}