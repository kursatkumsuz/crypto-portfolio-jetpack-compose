package com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth

import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(email: String, password: String) = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(authRepository.signIn(email, password).await()))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}


