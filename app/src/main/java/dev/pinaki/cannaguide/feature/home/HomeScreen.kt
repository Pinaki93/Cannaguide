package dev.pinaki.cannaguide.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.pinaki.cannaguide.R
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.data.store.JournalEntry
import dev.pinaki.cannaguide.data.store.MoodEntry
import dev.pinaki.cannaguide.di.rememberCommonDateFormatter
import dev.pinaki.cannaguide.di.rememberCommonTimeFormatter
import dev.pinaki.cannaguide.feature.moodtracker.rememberEmotionToMoodMapper
import dev.pinaki.cannaguide.ui.component.EmptyContent
import dev.pinaki.cannaguide.ui.component.FullScreenLoader
import dev.pinaki.cannaguide.ui.component.HSpacer16
import dev.pinaki.cannaguide.ui.component.JournalEntryComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: LazyPagingItems<JournalEntry>,
    addEntry: () -> Unit,
    addMood: () -> Unit,
    editEntry: (JournalEntry) -> Unit,
    loadMore: () -> Unit,
) {
    var showOptionsBottomSheet by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.home)) },
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Search"
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            showOptionsBottomSheet = true
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            )
        },
    ) { paddingValues ->
        if (showOptionsBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showOptionsBottomSheet = false
                }
            ) {
                Row(
                    modifier = Modifier.padding(
                        vertical = 24.dp,
                        horizontal = 24.dp
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = {
                        showOptionsBottomSheet = false
                        addEntry()
                    }) {
                        Text(
                            text = "Add Intake Entry",
                        )
                    }

                    HSpacer16()

                    OutlinedButton(onClick = {
                        showOptionsBottomSheet = false
                        addMood()
                    }) {
                        Text(
                            text = "Record Mood",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                }
            }
        }

        if (state.loadState.refresh == LoadState.Loading) {
            FullScreenLoader(modifier = Modifier.padding(paddingValues))
        } else if (state.itemCount == 0) {
            EmptyContent(stringResource(R.string.no_entry_added_yet))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
            ) {
                items(
                    items = state.itemSnapshotList,
                ) { entry ->
                    val timeFormatter = rememberCommonTimeFormatter()
                    val dateFormatter = rememberCommonDateFormatter()
                    val emotionToMoodMapper = rememberEmotionToMoodMapper()

                    when (entry) {
                        is IntakeEntry -> {
                            JournalEntryComponent(
                                onClick = {
                                    editEntry(entry)
                                },
                                emoji = entry.emoji,
                                line1 = when {
                                    !entry.amountConsumed.isNullOrEmpty() &&
                                            !entry.consumptionMethod.isNullOrEmpty() -> {
                                        stringResource(
                                            R.string.gms_consumed_via,
                                            entry.amountConsumed.toString(),
                                            entry.consumptionMethod.orEmpty()
                                        )
                                    }

                                    !entry.consumptionMethod.isNullOrEmpty() -> {
                                        entry.consumptionMethod
                                    }

                                    !entry.amountConsumed.isNullOrEmpty() -> {
                                        entry.amountConsumed
                                    }

                                    else -> {
                                        stringResource(id = R.string.intake_log)
                                    }
                                },
                                line2 = stringResource(
                                    R.string.at,
                                    dateFormatter.format(entry.entryDate),
                                    timeFormatter.format(entry.entryDate)
                                )
                            )
                        }

                        is MoodEntry -> {
                            JournalEntryComponent(
                                onClick = {
                                    editEntry(entry)
                                },
                                emoji = entry.emotion.emoji,
                                line1 = emotionToMoodMapper.map(entry.emotion).description,
                                line2 = stringResource(
                                    R.string.at,
                                    dateFormatter.format(entry.entryDate),
                                    timeFormatter.format(entry.entryDate)
                                )
                            )
                        }

                        null -> {

                        }
                    }
                    Divider()
                }
            }
        }
    }
}