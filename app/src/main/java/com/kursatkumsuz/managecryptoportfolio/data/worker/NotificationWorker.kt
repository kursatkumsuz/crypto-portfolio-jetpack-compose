package com.kursatkumsuz.managecryptoportfolio.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.notification.ReadNotificationPreferenceUseCase
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice
import com.kursatkumsuz.managecryptoportfolio.util.Notification
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getCoinUseCase: GetCoinUseCase,
    private val readNotificationPreferenceUseCase: ReadNotificationPreferenceUseCase
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        return try {
            // Check user's notification preference
            if (readNotificationPreferenceUseCase.invoke()) {
                getCoinUseCase.invoke().collect {
                    when (it) {
                        // If successful, show notification
                        is Response.Success -> {
                            it.data.data.map { i ->
                                if (i.symbol == "BTC") {
                                    Notification.showNotification(
                                        context = applicationContext,
                                        FormatCoinPrice.formatPrice(i.quote.USD.price)
                                    )
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
            // Return success result
            Result.success()

        } catch (e: Exception) {
            // If exception occurred, retry the task
            Result.retry()
        }

    }
}