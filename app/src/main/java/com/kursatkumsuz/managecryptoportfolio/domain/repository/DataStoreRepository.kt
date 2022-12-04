package com.kursatkumsuz.managecryptoportfolio.domain.repository


interface DataStoreRepository {

    suspend fun saveNotificationPreference(isActive : Boolean)

    suspend fun readNotificationPreference() : Boolean
}