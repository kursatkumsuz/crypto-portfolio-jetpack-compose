package com.kursatkumsuz.managecryptoportfolio.infrastructure.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.infrastructure.notification.Notification.showNotification
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getCoinUseCase: GetCoinUseCase,

) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        return try {
            getCoinUseCase.invoke().collect {
                when (it) {
                    is Response.Success -> {
                        it.data.data.map { i ->
                            if (i.symbol == "BTC") {
                                showNotification(
                                    context = applicationContext,
                                    formatPrice(i.quote.USD.price)
                                )
                            }
                        }
                    }
                    else -> {}

                }
            }
            Result.success()

        } catch (e: Exception) {
            Result.retry()
        }

    }
}