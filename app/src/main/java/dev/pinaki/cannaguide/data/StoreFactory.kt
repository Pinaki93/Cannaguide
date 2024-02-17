package dev.pinaki.cannaguide.data

import androidx.room.Room
import dev.pinaki.cannaguide.CannaguideApp
import dev.pinaki.cannaguide.data.store.PrimaryEmotionsStore

object StoreFactory {
    val db by lazy {
        Room.databaseBuilder(
            CannaguideApp.instance,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    val intakeStore get() = db.intakeEntryStore()
    val primaryEmotionsStore get() = PrimaryEmotionsStore()
    val moodEntryStore get() = db.moodEntryStore()
}