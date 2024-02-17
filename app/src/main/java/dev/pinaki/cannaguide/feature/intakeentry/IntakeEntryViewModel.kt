package dev.pinaki.cannaguide.feature.intakeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pinaki.cannaguide.data.StoreFactory
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.data.store.IntakeEntryStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IntakeEntryViewModel(
    private val dao: IntakeEntryStore = StoreFactory.intakeStore
) : ViewModel() {

    fun addEntry(entry: IntakeEntry) {
        viewModelScope.launch {
            dao.insert(entry)
        }
    }
}