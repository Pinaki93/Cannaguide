package dev.pinaki.cannaguide.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.pinaki.cannaguide.R
import dev.pinaki.cannaguide.di.rememberCommonDateFormatter
import dev.pinaki.cannaguide.di.rememberCommonTimeFormatter

@Composable
fun JournalEntryComponent(
    onClick: () -> Unit,
    emoji: String,
    line1: String,
    line2: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            style = MaterialTheme.typography.headlineLarge
        )
        HSpacer16()

        Column(
            Modifier.weight(1f)
        ) {
            Text(
                text = line1,
                style = MaterialTheme.typography.bodyLarge
            )

            val timeFormatter = rememberCommonTimeFormatter()
            val dateFormatter = rememberCommonDateFormatter()
            Text(
                modifier = Modifier.alpha(0.5f),
                text = line2,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}