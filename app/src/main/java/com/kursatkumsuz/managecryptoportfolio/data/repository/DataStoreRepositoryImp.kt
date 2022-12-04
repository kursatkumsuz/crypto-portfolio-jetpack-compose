package com.kursatkumsuz.managecryptoportfolio.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kursatkumsuz.managecryptoportfolio.domain.repository.DataStoreRepository
import com.kursatkumsuz.managecryptoportfolio.util.Constants.Companion.PREFERENCE_NOTIFICATION_KEY
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("notification_preference")

class NotificationRepositoryImp(
    private val context: Context) : DataStoreRepository {


    override suspend fun saveNotificationPreference(isActive: Boolean) {
        context.dataStore.edit {
            it[PREFERENCE_NOTIFICATION_KEY] = isActive
        }
    }

    override suspend fun readNotificationPreference(): Boolean {
        val data = context.dataStore.data.first()
        return data[PREFERENCE_NOTIFICATION_KEY] ?: true
    }

}