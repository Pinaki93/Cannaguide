package dev.pinaki.cannaguide.feature.intakeentry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.pinaki.cannaguide.R
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.di.rememberCommonDateFormatter
import dev.pinaki.cannaguide.di.rememberCommonTimeFormatter
import dev.pinaki.cannaguide.ui.component.EmptyContent
import dev.pinaki.cannaguide.ui.component.FullScreenLoader
import dev.pinaki.cannaguide.ui.component.HSpacer16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntakeEntryListScreen(
    state: IntakeEntryState,
    addEntry: () -> Unit,
    editEntry: (IntakeEntry) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.home)) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addEntry) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        if (state.loading) {
            FullScreenLoader(modifier = Modifier.padding(paddingValues))
        } else if (state.entries.isEmpty()) {
            EmptyContent(stringResource(R.string.no_entry_added_yet))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                items(items = state.entries) { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                editEntry(entry)
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = entry.emoji,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        HSpacer16()

                        Column(
                            Modifier.weight(1f)
                        ) {
                            val consumptionDetails = when {
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
                            }
                            Text(
                                text = consumptionDetails,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            val timeFormatter = rememberCommonTimeFormatter()
                            val dateFormatter = rememberCommonDateFormatter()
                            Text(
                                modifier = Modifier.alpha(0.5f),
                                text = stringResource(
                                    R.string.at,
                                    dateFormatter.format(entry.dateOfIntake),
                                    timeFormatter.format(entry.dateOfIntake)
                                ),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
}