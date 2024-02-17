package dev.pinaki.cannaguide.data.store

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import dev.pinaki.cannaguide.core.BaseDao
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Entity(tableName = "mood_entry")
@Stable
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    override val id: Int? = null,
    @ColumnInfo(name = "amount_consumed")
    val emotion: PrimaryEmotion,
    @ColumnInfo(name = "method_of_consumption")
    val description: String,
    @ColumnInfo(name = "date")
    override val entryDate: Date,
) : JournalEntry

@Dao
interface MoodEntryStore : BaseDao<MoodEntry> {
    @Query("select * from mood_entry order by date DESC limit :limit offset :offset")
    fun getEntries(limit: Int, offset: Int): Flow<List<MoodEntry>>

    @Query("select * from mood_entry where id=:id")
    suspend fun findById(id: Int): MoodEntry
}