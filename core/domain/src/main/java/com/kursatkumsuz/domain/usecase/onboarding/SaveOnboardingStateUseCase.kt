package com.kursatkumsuz.domain.usecase.onboarding

import com.kursatkumsuz.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveOnboardingStateUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isCompleted : Boolean) = dataStoreRepository.saveNotificationPreference(isCompleted)

}