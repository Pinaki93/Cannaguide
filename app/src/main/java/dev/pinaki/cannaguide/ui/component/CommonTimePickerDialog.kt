package dev.pinaki.cannaguide.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.pinaki.cannaguide.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTimePickerDialog(
    initialTime: Long,
    onDismissRequest: () -> Unit,
    onTimeSelected: (Long) -> Unit,
) {
    val initialTimeCalendar = remember(initialTime) {
        Calendar.getInstance()
            .apply {
                setTimeInMillis(initialTime)
            }
    }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)
        ) {
            val timePickerState = rememberTimePickerState(
                initialHour = initialTimeCalendar[Calendar.HOUR_OF_DAY],
                initialMinute = initialTimeCalendar[Calendar.MINUTE],
                is24Hour = false
            )
            TimePicker(
                state = timePickerState,
                layoutType = TimePickerLayoutType.Vertical,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(
                    onClick = {
                        val timeInMillis = initialTimeCalendar.apply {
                            set(
                                Calendar.HOUR_OF_DAY, timePickerState.hour
                            )
                            set(
                                Calendar.MINUTE, timePickerState.minute
                            )
                        }.timeInMillis
                        onTimeSelected(timeInMillis)
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}