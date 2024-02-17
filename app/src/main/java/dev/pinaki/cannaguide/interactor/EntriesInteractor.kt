package dev.pinaki.cannaguide.interactor

import dev.pinaki.cannaguide.paging.Page
import dev.pinaki.cannaguide.paging.PagedData
import dev.pinaki.cannaguide.data.StoreFactory
import dev.pinaki.cannaguide.data.store.IntakeEntryStore
import dev.pinaki.cannaguide.data.store.JournalEntry
import dev.pinaki.cannaguide.data.store.MoodEntryStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class EntriesInteractor(
    private val moodEntryStore: MoodEntryStore = StoreFactory.moodEntryStore,
    private val intakeEntryStore: IntakeEntryStore = StoreFactory.intakeStore,
) {


    suspend fun get(page: Page): Flow<PagedData<JournalEntry>> {
        val moodEntries = moodEntryStore.getEntries(page.limit, page.offset)
        val intakeEntries = intakeEntryStore.getEntries(page.limit, page.offset)
        return merge(moodEntries, intakeEntries)
            .map { entries ->
                PagedData(
                    data = entries,
                    nextPage = if (entries.isEmpty()) {
                        page.ended()
                    } else {
                        page.incremented()
                    }
                )
            }
    }
}