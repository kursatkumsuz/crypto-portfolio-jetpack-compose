package com.kursatkumsuz.managecryptoportfolio.domain.usecase.auth

import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(authRepository.getUserInfo().await()))
        }catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}