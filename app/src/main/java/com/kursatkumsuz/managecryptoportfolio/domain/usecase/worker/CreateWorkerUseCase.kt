package com.kursatkumsuz.managecryptoportfolio.domain.usecase.worker

import com.kursatkumsuz.managecryptoportfolio.infrastructure.worker.NotificationWorkerProvider
import javax.inject.Inject

class CreateWorkerUseCase @Inject constructor(
    private val notificationWorkerProvider: NotificationWorkerProvider
) {
    operator fun invoke() = notificationWorkerProvider.periodicRequest()
}