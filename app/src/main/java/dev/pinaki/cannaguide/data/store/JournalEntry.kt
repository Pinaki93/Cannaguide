package dev.pinaki.cannaguide.data.store

import java.util.Date

sealed interface JournalEntry {
    val id: Int?
    val entryDate: Date
}