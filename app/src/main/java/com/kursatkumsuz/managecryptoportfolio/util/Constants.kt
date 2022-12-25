package com.kursatkumsuz.managecryptoportfolio.util

import androidx.datastore.preferences.core.booleanPreferencesKey

class Constants {

    companion object {
        const val BASE_URL =  "https://pro-api.coinmarketcap.com/v1/"

        val PREFERENCE_NOTIFICATION_KEY = booleanPreferencesKey("notification")

        const val CHANNEL_ID = "LastPrice"
        const val CHANNEL_NAME = "LastPriceChannel"
        const val CHANNEL_DESCRIPTION = "Last Price Channel Description"
    }
}