package com.kursatkumsuz.domain.usecase.portfolio

import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.domain.repository.AuthRepository
import com.kursatkumsuz.domain.repository.PortfolioRepository
import javax.inject.Inject

class AddToPortfolioUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        portfolioModel: PortfolioModel
    ) {
        try {
            portfolioRepository.addToPortfolio(authRepository.userUid(), portfolioModel)
        } catch (e: Exception) { }
    }
}