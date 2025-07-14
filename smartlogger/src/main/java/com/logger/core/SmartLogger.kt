package com.logger.core

import android.app.Application
import com.logger.config.SmartLoggerConfig
import com.logger.repository.LoggerRepository
import com.logger.storage.LogEvent
import com.logger.worker.LogUploadWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Library entry point.
 * The host app initialise and calls the logEvent to process events.
 * init method must be called before logging events else it will throw exception.
 * */

//Better design approach have several benefits:
//1.Saves resources (single scope) and avoiding multiple scope every time
//2.Structured concurrency, better control over child jobs.
//3.all are run under a managed job hierarchy
private object LoggerScope : CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.IO)

class SmartLogger private constructor(private val repository: LoggerRepository) {

    fun logEvent(eventName: String, payload: Map<String, Any>) {
        LoggerScope.launch(Dispatchers.IO) {
            repository.logEvent(LogEvent(eventName, payload))
        }
    }


    companion object {
        @Volatile
        private var instance: SmartLogger? = null

        fun init(application: Application, serverUrl: String): SmartLogger {
            SmartLoggerConfig.serverUrl = serverUrl
            LogUploadWorker.scheduleLogUpload(application)
            return instance ?: synchronized(this) {
                instance ?: SmartLogger(
                    LoggerRepository(application, serverUrl)
                ).also { instance = it }
            }
        }

        fun get(): SmartLogger = instance
            ?: throw IllegalStateException("SmartLogger.init() must be called before use.")
    }
}
