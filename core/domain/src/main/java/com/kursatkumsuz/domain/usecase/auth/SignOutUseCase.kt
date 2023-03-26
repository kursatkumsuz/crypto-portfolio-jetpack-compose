package com.kursatkumsuz.domain.usecase.auth

import com.kursatkumsuz.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() {
        authRepository.signOut()
    }
}