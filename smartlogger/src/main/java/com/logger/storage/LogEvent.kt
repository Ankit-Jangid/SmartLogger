package com.logger.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.logger.utils.Converters
import java.util.UUID

@Entity(tableName = "log_events")
@TypeConverters(Converters::class)
data class LogEvent(
    val name: String,
    val data: Map<String, Any>,
    val timestamp: Long = System.currentTimeMillis(),
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)
