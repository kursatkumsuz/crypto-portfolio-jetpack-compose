package com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth

import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke() = flow {
        emit(authRepository.isLoggedIn())
    }
}