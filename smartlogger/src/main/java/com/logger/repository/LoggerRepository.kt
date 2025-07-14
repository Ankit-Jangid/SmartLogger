package com.logger.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import com.logger.dispatch.EventDispatcher
import com.logger.storage.LogDatabase
import com.logger.storage.LogEvent
import com.logger.utils.NetworkUtil

/**
 * Repository which is responsible for storing and dispatching events.
 * For simplicity, all the events are processed (storing, dispatching all at once)
 * but we can design it in batches in case of heavy logs.
 * */
class LoggerRepository(val context: Context, serverUrl: String) {
    private val db = LogDatabase.getInstance(context)
    private val dispatcher = EventDispatcher(serverUrl)

     @SuppressLint("MissingPermission")
     suspend fun logEvent(event: LogEvent) {
        db.eventDao().insert(event)
        if (NetworkUtil.isOnline(context)) {
            dispatcher.tryDispatch()
        }
        dispatcher.tryDispatch()
    }
}
