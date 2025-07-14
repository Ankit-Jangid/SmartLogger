package com.logger.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogEventDao {
    @Insert
    suspend fun insert(event: LogEvent)

    @Query("SELECT * FROM log_events")
    suspend fun getAll(): List<LogEvent>

    @Query("DELETE FROM log_events")
    suspend fun clearAll()
}
