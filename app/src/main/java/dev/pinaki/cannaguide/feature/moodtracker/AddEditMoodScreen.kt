package dev.pinaki.cannaguide.feature.moodtracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.pinaki.cannaguide.R
import dev.pinaki.cannaguide.data.store.MoodEntry
import dev.pinaki.cannaguide.data.store.PrimaryEmotion
import dev.pinaki.cannaguide.data.store.rememberPrimaryEmotionsStore
import dev.pinaki.cannaguide.di.rememberCommonDateFormatter
import dev.pinaki.cannaguide.di.rememberCommonTimeFormatter
import dev.pinaki.cannaguide.ui.component.CommonDatePickerDialog
import dev.pinaki.cannaguide.ui.component.CommonDropDownMenu
import dev.pinaki.cannaguide.ui.component.CommonTimePickerDialog
import dev.pinaki.cannaguide.ui.component.VSpacer16
import dev.pinaki.cannaguide.ui.component.VSpacer24
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMoodScreen(
    isEditMode: Boolean = false,
    entry: MoodEntry? = null,
    emotions: () -> List<PrimaryEmotion>,
    addEntry: (MoodEntry) -> Unit,
    goBack: () -> Unit,
    deleteEntry: (MoodEntry) -> Unit = {},
) {
    val openedTime = rememberSaveable {
        System.currentTimeMillis()
    }

    val dateFormatter = rememberCommonDateFormatter()
    val timeFormatter = rememberCommonTimeFormatter()
    val primaryEmotionsStore = rememberPrimaryEmotionsStore()
    val coroutineScope = rememberCoroutineScope()

    var emotion by rememberSaveable(entry) {
        mutableStateOf(entry?.emotion)
    }
    val emotionToMoodMapper = rememberEmotionToMoodMapper()

    val moods = remember(emotions) {
        emotions().map { emotionToMoodMapper.map(it) }
    }
    var notes by remember(emotions) {
        mutableStateOf(entry?.description.orEmpty())
    }
    var selectedDate by rememberSaveable(entry) {
        mutableStateOf(entry?.entryDate?.time ?: openedTime)
    }
    val selectedDateStr = remember(selectedDate) {
        dateFormatter.format(selectedDate)
    }
    val selectedTimeStr = remember(selectedDate) {
        timeFormatter.format(selectedDate)
    }
    var showDatePickerDialog by rememberSaveable(entry) {
        mutableStateOf(false)
    }
    var showTimePickerDialog by rememberSaveable(entry) {
        mutableStateOf(false)
    }
    var showDeletePrompt by rememberSaveable(entry) {
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate,
        initialDisplayMode = DisplayMode.Picker,
    )
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.record_mood,)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (isEditMode && entry != null) {
                        IconButton(onClick = {
                            showDeletePrompt = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete Entry"
                            )
                        }
                    }
                },

                )
        }
    ) { paddingValues ->
        if (showDatePickerDialog) {
            CommonDatePickerDialog(
                datePickerState = datePickerState,
                onDateSelected = { selectedDateMillis ->
                    selectedDateMillis?.let { selectedDate = it }
                },
                onDismiss = {
                    showDatePickerDialog = false
                }
            )
        }

        if (showTimePickerDialog) {
            CommonTimePickerDialog(
                initialTime = selectedDate,
                onDismissRequest = {
                    showTimePickerDialog = false
                },
                onTimeSelected = { selectedDateMillis ->
                    selectedDate = selectedDateMillis
                }
            )
        }

        if (showDeletePrompt) {
            AlertDialog(
                onDismissRequest = { showDeletePrompt = false },
                title = {
                    Text(text = stringResource(R.string.confirm_delete))
                },
                text = {
                    Text(text = stringResource(R.string.are_you_sure_you_want_to_delete_this_entry))
                },
                confirmButton = {
                    TextButton(onClick = {
                        entry?.let(deleteEntry)
                        showDeletePrompt = false
                        goBack()
                    }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeletePrompt = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            CommonDropDownMenu(
                title = stringResource(R.string.select_an_emotion),
                items = moods,
                selectedItem = emotion?.let(emotionToMoodMapper::map),
                stringAdapter = { "${it.emoji} ${it.description}" },
                onItemSelected = {
                    emotion = PrimaryEmotion.find(it.emoji)
                }
            )

            VSpacer16()

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.lbl_emotions_description))
                },
                minLines = 3,
                value = notes,
                onValueChange = {
                    notes = it
                }
            )

            VSpacer16()

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.intake_date))
                },
                value = selectedDateStr,
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            showDatePickerDialog = true
                        },
                        imageVector = Icons.Default.DateRange,
                        contentDescription = ""
                    )
                }
            )

            VSpacer16()

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.intake_time))
                },
                value = selectedTimeStr,
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            showTimePickerDialog = true
                        },
                        imageVector = Icons.Default.Add,
                        contentDescription = ""
                    )
                }
            )

            VSpacer24()

            val selectAMoodErrorMessage = stringResource(R.string.please_select_a_mood)
            Button(
                enabled = !isEditMode || entry != null,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    val localEmotion = emotion ?: run {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(selectAMoodErrorMessage)
                        }
                        return@Button
                    }
                    if (isEditMode && entry != null) {
                        addEntry(
                            entry.copy(
                                emotion = localEmotion,
                                description = notes,
                                entryDate = Date(selectedDate),
                            )
                        )
                    } else if (!isEditMode) {
                        addEntry(
                            MoodEntry(
                                emotion = localEmotion,
                                description = notes,
                                entryDate = Date(selectedDate),
                            )
                        )
                    }

                    goBack()
                }) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.button_continue)
                )
            }
        }
    }
}