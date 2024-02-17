package dev.pinaki.cannaguide.feature.intakeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pinaki.cannaguide.data.StoreFactory
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.data.store.IntakeEntryStore
import kotlinx.coroutines.launch

class EditIntakeEntryViewModel(
    private val store: IntakeEntryStore = StoreFactory.intakeStore
) : ViewModel() {

    suspend fun getEntry(id: Int): IntakeEntry? {
        return store.findById(id)
    }

    fun editEntry(entry: IntakeEntry) {
        viewModelScope.launch {
            store.update(entry)
        }
    }

    fun deleteEntry(entry: IntakeEntry) {
        viewModelScope.launch {
            store.delete(entry)
        }
    }

}