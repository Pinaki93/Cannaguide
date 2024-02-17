package dev.pinaki.cannaguide.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.data.store.IntakeEntryStore
import dev.pinaki.cannaguide.data.store.MoodEntry
import dev.pinaki.cannaguide.data.store.MoodEntryStore
import dev.pinaki.cannaguide.data.store.PrimaryEmotion
import java.util.Date

@Database(
    entities = [IntakeEntry::class, MoodEntry::class],
    version = 1
)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun intakeEntryStore(): IntakeEntryStore
    abstract fun moodEntryStore(): MoodEntryStore
}


object AppTypeConverters {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromEmotion(emotion: PrimaryEmotion): String {
        return emotion.emoji
    }

    @TypeConverter
    fun toEmotion(emoji: String): PrimaryEmotion {
        return PrimaryEmotion.find(emoji)
    }
}