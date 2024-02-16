package dev.pinaki.cannaguide.feature.intakeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pinaki.cannaguide.data.StoreFactory
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.data.store.IntakeEntryDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IntakeEntryViewModel(
    private val dao: IntakeEntryDao = StoreFactory.intakeStore
) : ViewModel() {

    val entries = dao.getAllEntries()
        .map { IntakeEntryState(false, it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = IntakeEntryState()
        )

    fun addEntry(entry: IntakeEntry) {
        viewModelScope.launch {
            dao.insert(entry)
        }
    }
}