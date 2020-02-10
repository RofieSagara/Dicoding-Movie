package com.sagara.dicodingmovie.schedulers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sagara.dicodingmovie.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import kotlin.Exception

class ReleaseTodayWorkManager(
    appContext: Context, workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams), KoinComponent {
    private val channelId = "Release Reminder"

    private val movieService: MovieService by inject()

    override suspend fun doWork(): Result {
        Timber.i("Start working for download new release!")
        val data = withContext(Dispatchers.IO){
            return@withContext try{
                movieService.release(DateTime.now())
            }catch (ex: Exception){
                Timber.e(ex)
                null
            }

        }

        var content = ""
        data?.results?.map {
            content += "${it.title}\n"
        }

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Release Reminder")
            .setContentText("New release")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(content))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, "Release Reminder", importance)
                .apply {
                    description = "Release new movie"
                }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notification.flags = notification.flags or NotificationCompat.FLAG_AUTO_CANCEL
        notificationManager.notify(channelId, 2, notification)

        return Result.success()
    }
}