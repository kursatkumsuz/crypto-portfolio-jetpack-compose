package com.kursatkumsuz.managecryptoportfolio.domain.usecase.notification

import com.kursatkumsuz.managecryptoportfolio.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadNotificationPreferenceUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() = dataStoreRepository.readNotificationPreference()
}