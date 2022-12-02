package com.kursatkumsuz.managecryptoportfolio.infrastructure.worker

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class NotificationWorkerProvider(context: Context) {

    val workManager = WorkManager.getInstance(context)

    fun periodicRequest() {
        val request = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(request)
    }
}