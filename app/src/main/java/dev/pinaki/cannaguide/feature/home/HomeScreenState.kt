package dev.pinaki.cannaguide.feature.home

import dev.pinaki.cannaguide.data.store.JournalEntry

data class HomeScreenState(
    val loading: Boolean = false,
    val entries: List<JournalEntry> = emptyList()
)