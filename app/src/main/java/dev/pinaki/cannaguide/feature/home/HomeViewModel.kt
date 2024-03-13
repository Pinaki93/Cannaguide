package dev.pinaki.cannaguide.feature.home

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.pinaki.cannaguide.paging.EntriesPagingSource

class HomeViewModel(
    private val entriesPagingSource: EntriesPagingSource = EntriesPagingSource()
) : ViewModel() {

    val entries = Pager(PagingConfig(pageSize = 50)) { entriesPagingSource }.flow

    init {
        loadMore()
    }

    fun loadMore() {

    }
}

private const val TAG = "HomeViewModel"