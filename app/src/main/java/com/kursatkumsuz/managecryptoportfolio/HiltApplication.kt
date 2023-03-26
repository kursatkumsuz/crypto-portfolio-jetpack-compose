package com.kursatkumsuz.managecryptoportfolio

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.kursatkumsuz.domain.usecase.worker.CreateWorkerUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltApplication  : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory


    @Inject lateinit var createWorkerUseCase: com.kursatkumsuz.domain.usecase.worker.CreateWorkerUseCase

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        createWorkerUseCase.invoke()
    }

}