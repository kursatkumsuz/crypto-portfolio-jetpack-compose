package com.kursatkumsuz.managecryptoportfolio.di

import com.kursatkumsuz.managecryptoportfolio.data.remote.ApiService
import com.kursatkumsuz.managecryptoportfolio.data.repository.CoinRepositoryImp
import com.kursatkumsuz.managecryptoportfolio.domain.repository.CoinRepository
import com.kursatkumsuz.managecryptoportfolio.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit () = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideCoinRepository(apiService: ApiService) : CoinRepository {
        return CoinRepositoryImp(apiService)
    }

}