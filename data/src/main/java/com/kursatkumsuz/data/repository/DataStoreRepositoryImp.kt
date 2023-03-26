package com.kursatkumsuz.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kursatkumsuz.util.Constants.Companion.PREFERENCE_NOTIFICATION_KEY
import com.kursatkumsuz.util.Constants.Companion.PREFERENCE_ONBOARDING_KEY
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("notification_preference")

class NotificationRepositoryImp(
    private val context: Context
) : com.kursatkumsuz.domain.repository.DataStoreRepository {


    override suspend fun saveNotificationPreference(isActive: Boolean) {
        context.dataStore.edit {
            it[PREFERENCE_NOTIFICATION_KEY] = isActive
        }
    }

    override suspend fun readNotificationPreference(): Boolean {
        val data = context.dataStore.data.first()
        return data[PREFERENCE_NOTIFICATION_KEY] ?: true
    }

    override suspend fun saveOnboardingState(isCompleted: Boolean) {
        context.dataStore.edit {
            it[PREFERENCE_ONBOARDING_KEY] = isCompleted
        }
    }

    override suspend fun readOnboardingState(): Boolean {
        val data = context.dataStore.data.first()
        return data[PREFERENCE_ONBOARDING_KEY] ?: true    }

}