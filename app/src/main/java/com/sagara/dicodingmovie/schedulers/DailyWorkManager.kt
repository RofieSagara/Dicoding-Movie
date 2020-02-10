package com.sagara.dicodingmovie.schedulers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class DailyWorkManager(
    appContext: Context, workerParams: WorkerParameters
): Worker(appContext, workerParams) {
    private val channelId = "Daily Reminder"

    override fun doWork(): Result {
        Timber.i("Start working for notification!")
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Daily Reminder")
            .setContentText("New movies coming for you!")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, "Daily Reminder", importance)
                .apply {
                    description = "Reminder for return to app"
                }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notification.flags = notification.flags or NotificationCompat.FLAG_AUTO_CANCEL
        notificationManager.notify(channelId, 1, notification)

        return Result.success()
    }
}