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

@Entity(tableName = "intake_entry")
@Stable
data class IntakeEntry(
    @PrimaryKey(autoGenerate = true)
    override val id: Int? = null,
    @ColumnInfo(name = "amount_consumed")
    val amountConsumed: String?,
    @ColumnInfo(name = "method_of_consumption")
    val consumptionMethod: String?,
    @ColumnInfo(name = "date")
    override val entryDate: Date,
    @ColumnInfo(name = "emoji")
    val emoji: String,
) : JournalEntry

@Dao
interface IntakeEntryStore : BaseDao<IntakeEntry> {
    @Query("select * from intake_entry where id=:id")
    suspend fun findById(id: Int): IntakeEntry?

    @Query("select * from intake_entry order by date DESC limit :limit offset :offset")
    suspend fun getEntries(limit: Int, offset: Int): List<IntakeEntry>
}