package dev.pinaki.cannaguide.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.pinaki.cannaguide.data.StoreFactory
import dev.pinaki.cannaguide.data.store.IntakeEntryStore
import dev.pinaki.cannaguide.data.store.JournalEntry
import dev.pinaki.cannaguide.data.store.MoodEntryStore

class EntriesPagingSource(
    private val moodEntryStore: MoodEntryStore = StoreFactory.moodEntryStore,
    private val intakeEntryStore: IntakeEntryStore = StoreFactory.intakeStore,
) : PagingSource<Int, JournalEntry>() {
    override fun getRefreshKey(state: PagingState<Int, JournalEntry>): Int? {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JournalEntry> {
        val pageNumber = params.key ?: 0

        val loadSize = params.loadSize
        val entries = moodEntryStore.getEntries(loadSize, loadSize * (pageNumber)) +
                intakeEntryStore.getEntries(loadSize, loadSize * (pageNumber))

        return LoadResult.Page(
            data = entries.sortedByDescending { it.entryDate },
            prevKey = null,
            nextKey = if (entries.isEmpty()) pageNumber + 1 else null
        )
    }
}