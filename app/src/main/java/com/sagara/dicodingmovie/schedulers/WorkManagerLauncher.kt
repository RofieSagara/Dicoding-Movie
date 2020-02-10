package com.sagara.dicodingmovie.schedulers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import org.joda.time.DateTime
import org.joda.time.Duration
import timber.log.Timber
import java.util.concurrent.TimeUnit


class WorkManagerLauncher(private val context: Context) {

    //Start Daily work manager at 07:00 AM with delay
    fun startDailyWork(){
        Timber.i("DailyWorkManager created!")
        // start at 07:00 AM
        val startTime = 7

        val delay = if (DateTime.now().hourOfDay < startTime) {
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusHours(startTime)
            ).standardMinutes
        } else {
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusDays(1).plusHours(
                    startTime
                )
            ).standardMinutes
        }

        val workRequest = PeriodicWorkRequest.Builder(
            DailyWorkManager::class.java,
            24,
            TimeUnit.HOURS,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .addTag("send_daily_reminder_periodic")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "send_daily_reminder_periodic",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
    }

    fun stopDailyWork(){
        Timber.i("DailyWorkManager killed!")
        WorkManager.getInstance(context)
            .cancelUniqueWork("send_daily_reminder_periodic")
    }

    fun startReleaseWork(){
        Timber.i("ReleaseTodayWorkManager created!")
        // start at 08:00 AM
        val startTime = 8

        val delay = if (DateTime.now().hourOfDay < startTime) {
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusHours(startTime)
            ).standardMinutes
        } else {
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusDays(1).plusHours(
                    startTime
                )
            ).standardMinutes
        }

        val workRequest = PeriodicWorkRequest.Builder(
            ReleaseTodayWorkManager::class.java,
            24,
            TimeUnit.HOURS,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .addTag("send_release_reminder_periodic")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "send_release_reminder_periodic",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
    }

    fun stopReleaseWork(){
        Timber.i("ReleaseTodayWorkManager killed!")
        WorkManager.getInstance(context)
            .cancelUniqueWork("send_release_reminder_periodic")
    }
}