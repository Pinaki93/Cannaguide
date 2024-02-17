package dev.pinaki.cannaguide.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pinaki.cannaguide.paging.Page
import dev.pinaki.cannaguide.interactor.EntriesInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val entriesInteractor: EntriesInteractor = EntriesInteractor()
) : ViewModel() {
    private val _state = MutableStateFlow(
        HomeScreenState(loading = true)
    )
    val state = _state.asStateFlow()
    private var nextPage: Page = Page()

    init {
        loadMore()
    }

    fun loadMore() {
        if (!nextPage.hasNothing) {
            viewModelScope.launch {
                entriesInteractor.get(nextPage)
                    .collectLatest { data ->
                        _state.update { state ->
                            state.copy(
                                loading = false,
                                (state.entries + data.data)
                                    .toSet()
                                    .toList()
                                    .sortedByDescending { it.entryDate }
                            )
                        }
                    }
            }
        }
    }


}