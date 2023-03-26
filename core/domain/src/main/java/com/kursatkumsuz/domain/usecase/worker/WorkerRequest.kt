package com.kursatkumsuz.domain.usecase.worker

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WorkerRequest(context: Context) {

    private val workManager = WorkManager.getInstance(context)

    /**
     * Schedule a periodic work request to be executed by WorkManager.
     * The request will be executed every 60 minutes, starting with a 15 minutes delay.
     */
    fun periodicRequest() {
        val request = PeriodicWorkRequestBuilder<NotificationWorker>(60, TimeUnit.MINUTES)
            .setInitialDelay(60, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(request)
    }
}