package com.kursatkumsuz.managecryptoportfolio.infrastructure.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.kursatkumsuz.managecryptoportfolio.R

object Notification {

    fun showNotification(context: Context, price: String) {
        createNotification(context, price)
    }

    private fun createNotification(context: Context, price: String) {
        val builder: NotificationCompat.Builder
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "LastPrice"
        val channelName = "LastPriceChannel"
        val channelDescription = "LastPriceDescription"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var channel: NotificationChannel? = notificationManager.getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            channel.description = channelDescription
            notificationManager.createNotificationChannel(channel)
        }

        builder = NotificationCompat.Builder(context, channelId)
        builder
            .setContentTitle("Bitcoin Current Price")
            .setContentText("Bitcoin Price :  $price")
            .setSmallIcon(R.drawable.ic_onboard_second)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }
}