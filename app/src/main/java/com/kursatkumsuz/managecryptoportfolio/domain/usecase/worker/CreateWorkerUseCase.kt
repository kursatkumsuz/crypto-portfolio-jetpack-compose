package com.kursatkumsuz.managecryptoportfolio.domain.usecase.worker

import com.kursatkumsuz.managecryptoportfolio.data.worker.WorkerRequest
import javax.inject.Inject

class CreateWorkerUseCase @Inject constructor(
    private val notificationWorkerProvider: WorkerRequest
) {
    operator fun invoke() = notificationWorkerProvider.periodicRequest()
}