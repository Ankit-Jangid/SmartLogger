package com.logger.dispatch

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson
import com.logger.storage.LogDatabase

/**
 * Class responsible for dispatching events to server.
 *
 * Uses IO thread making thread safe to call from any thread even ui thread,
 * and the tryDispatch method will offload it's tasks to the IO thread.
 *
 * */
class EventDispatcher(private val serverUrl: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun tryDispatch() = withContext(Dispatchers.IO) {
        val events = LogDatabase.instance?.eventDao()?.getAll() ?: return@withContext
        if (events.isEmpty()) return@withContext

        val json = gson.toJson(events)
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder().url(serverUrl).post(body).build()

        try {
            val response = client.newCall(request).execute()
            Log.d("EventDispatcher", "response: ${response.body?.string()}")
            if (response.isSuccessful) {
                LogDatabase.instance?.eventDao()?.clearAll()
            }
        } catch (e: Exception) {
            Log.e("EventDispatcher", " failed to upload logs with exception: ", e)
        }
    }
}
