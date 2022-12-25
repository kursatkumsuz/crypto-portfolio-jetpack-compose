package com.kursatkumsuz.managecryptoportfolio.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kursatkumsuz.managecryptoportfolio.data.local.FavoritesDao
import com.kursatkumsuz.managecryptoportfolio.data.local.FavoritesDatabase
import com.kursatkumsuz.managecryptoportfolio.data.remote.ApiService
import com.kursatkumsuz.managecryptoportfolio.data.repository.*
import com.kursatkumsuz.managecryptoportfolio.data.worker.WorkerRequest
import com.kursatkumsuz.managecryptoportfolio.domain.repository.*
import com.kursatkumsuz.managecryptoportfolio.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDao(favoritesDatabase: FavoritesDatabase) = favoritesDatabase.getDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        FavoritesDatabase::class.java,
        "favorites_database"
    ).build()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideCoinRepository(apiService: ApiService): CoinRepository {
        return CoinRepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(dao: FavoritesDao): FavoritesRepository {
        return FavoritesRepositoryImp(dao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImp(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun providePortfoliohRepository(firebaseFirestore: FirebaseFirestore): PortfolioRepository {
        return PortfolioRepositoryImp(firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideWorkerRequest(@ApplicationContext context: Context) =
        WorkerRequest(context)

    @Provides
    @Singleton
    fun provideNotificationRepository(@ApplicationContext context: Context): DataStoreRepository {
        return NotificationRepositoryImp(context)
    }
}