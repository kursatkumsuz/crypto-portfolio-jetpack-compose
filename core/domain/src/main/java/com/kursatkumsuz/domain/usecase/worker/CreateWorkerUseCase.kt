package com.kursatkumsuz.domain.usecase.worker

import javax.inject.Inject

class CreateWorkerUseCase @Inject constructor(
    private val notificationWorkerProvider: WorkerRequest
) {
    operator fun invoke() = notificationWorkerProvider.periodicRequest()
}