package dev.pinaki.cannaguide.feature.intakeentry

import dev.pinaki.cannaguide.data.store.IntakeEntry

data class IntakeEntryState(
    val loading: Boolean = true,
    val entries: List<IntakeEntry> = emptyList()
)