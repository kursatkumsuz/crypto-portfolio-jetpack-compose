package com.kursatkumsuz.managecryptoportfolio.domain.usecase.portfolio

import com.kursatkumsuz.managecryptoportfolio.domain.repository.PortfolioRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddToPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) {

    operator fun invoke(coinName: String, coinSymbol: String, coinPrice: String) = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(portfolioRepository.addToPortfolio(coinName, coinSymbol, coinPrice).await()))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}