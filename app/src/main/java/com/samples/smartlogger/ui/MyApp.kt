package com.samples.smartlogger.ui

import android.app.Application
import com.logger.core.SmartLogger


const val serverUrl = "https://smartlogger2.free.beeceptor.com/log"

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SmartLogger.init(this, serverUrl)
    }
}
