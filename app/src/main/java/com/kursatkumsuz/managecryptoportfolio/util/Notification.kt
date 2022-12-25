package com.kursatkumsuz.managecryptoportfolio.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.kursatkumsuz.managecryptoportfolio.R

object Notification {

    /**
     *Shows a notification
     *@param context The context to use for creating the notification
     *@param price The Bitcoin price to display in the notification
     */
    fun showNotification(context: Context, price: String) {
        createNotification(context, price)
    }

    /**
     *Creates a notification
     *@param context The context to use for creating the notification
     *@param price The Bitcoin price to display in the notification
     */
    private fun createNotification(context: Context, price: String) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var channel: NotificationChannel? = notificationManager.getNotificationChannel(Constants.CHANNEL_ID)

        if (channel == null) {
            channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, channelPriority)
            channel.description = Constants.CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }

        createNotification(context, price, notificationManager)

    }

    private fun createNotification(
        context: Context,
        price: String,
        notificationManager: NotificationManager
    ) {

        // Configure notification builder
        val builder: NotificationCompat.Builder = NotificationCompat
            .Builder(context, Constants.CHANNEL_ID)
            .setContentTitle("Bitcoin Current Price")
            .setContentText("Bitcoin Price :  $price")
            .setSmallIcon(R.drawable.ic_onboard_second)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())

    }
}