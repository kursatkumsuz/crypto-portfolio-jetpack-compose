package com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth

import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
}