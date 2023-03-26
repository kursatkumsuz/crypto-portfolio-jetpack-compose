package com.kursatkumsuz.domain.usecase.auth

import com.kursatkumsuz.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(name: String, email: String, password: String) = flow {
        try {
            emit(com.kursatkumsuz.util.Response.Loading)
            emit(com.kursatkumsuz.util.Response.Success(authRepository.signUp(name, email, password).await()))

        } catch (e: Exception) {
            emit(com.kursatkumsuz.util.Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}