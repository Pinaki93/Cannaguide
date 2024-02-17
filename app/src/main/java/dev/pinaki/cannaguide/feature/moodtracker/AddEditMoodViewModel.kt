package dev.pinaki.cannaguide.feature.moodtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pinaki.cannaguide.data.StoreFactory
import dev.pinaki.cannaguide.data.store.MoodEntry
import dev.pinaki.cannaguide.data.store.MoodEntryStore
import dev.pinaki.cannaguide.data.store.PrimaryEmotionsStore
import kotlinx.coroutines.launch

class AddEditMoodViewModel(
    private val moodEntryStore: MoodEntryStore = StoreFactory.moodEntryStore,
    emotionsStore: PrimaryEmotionsStore = StoreFactory.primaryEmotionsStore,
) : ViewModel() {

    val emotions = emotionsStore.primaryEmotionsList

    fun addMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            moodEntryStore.insert(moodEntry)
        }
    }

    fun editEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            moodEntryStore.update(moodEntry)
        }
    }

    fun deleteEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            moodEntryStore.delete(moodEntry)
        }
    }

    suspend fun getEntry(id: Int): MoodEntry {
        return moodEntryStore.findById(id)
    }
}