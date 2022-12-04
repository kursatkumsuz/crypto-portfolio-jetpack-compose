package com.kursatkumsuz.managecryptoportfolio.domain.usecase.notification

import com.kursatkumsuz.managecryptoportfolio.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveNotificationPreferenceUseCase @Inject constructor(
    private val notificationRepository: DataStoreRepository
) {

    suspend operator fun invoke(isActive : Boolean) = notificationRepository.saveNotificationPreference(isActive)
}