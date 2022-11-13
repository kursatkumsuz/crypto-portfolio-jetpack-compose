package com.kursatkumsuz.managecryptoportfolio.domain.usecase.favorite

import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.domain.repository.FavoritesRepository
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    operator fun invoke(): Flow<Response<List<FavoritesEntity>>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(favoritesRepository.getFavorites()))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unexpected Error!"))
        }
    }
}