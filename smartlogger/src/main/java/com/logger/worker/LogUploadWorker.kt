package com.logger.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.logger.dispatch.EventDispatcher
import com.logger.config.SmartLoggerConfig
import java.util.concurrent.TimeUnit

/**
 * Right now if the device is online it will push the data immediately.
 * If it's offline it will keep storing it locally.
 *
 * If the device comes back online, and user reacts(any event) it will kick back and will sync data.
 * For more complex logic we can observer network state using BroadcastReceiver for listening
 * network events
 *
 * */
class LogUploadWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val dispatcher = EventDispatcher(SmartLoggerConfig.serverUrl)
        return try {
            dispatcher.tryDispatch()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun scheduleLogUpload(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<LogUploadWorker>(10, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "LogUploadWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}

