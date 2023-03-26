package com.kursatkumsuz.domain.usecase.notification

import com.kursatkumsuz.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadNotificationPreferenceUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() = dataStoreRepository.readNotificationPreference()
}