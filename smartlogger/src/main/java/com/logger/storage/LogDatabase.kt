package com.logger.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.logger.utils.Converters

@Database(entities = [LogEvent::class], version = 1)
@TypeConverters(Converters::class)
abstract class LogDatabase : RoomDatabase() {
    abstract fun eventDao(): LogEventDao

    companion object {
        @Volatile var instance: LogDatabase? = null

        fun getInstance(context: Context): LogDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LogDatabase::class.java,
                    "smart_logger_db"
                ).build().also { instance = it }
            }
        }
    }
}
