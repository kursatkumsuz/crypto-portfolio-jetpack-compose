package com.kursatkumsuz.domain.repository


interface DataStoreRepository {

    suspend fun saveNotificationPreference(isActive : Boolean)

    suspend fun readNotificationPreference() : Boolean

    suspend fun saveOnboardingState(isCompleted : Boolean)

    suspend fun readOnboardingState() : Boolean
}