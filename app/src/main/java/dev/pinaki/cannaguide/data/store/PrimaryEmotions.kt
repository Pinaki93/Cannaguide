package dev.pinaki.cannaguide.data.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver


enum class PrimaryEmotion(val emoji: String) {
    ANGER("😡"),
    SADNESS("😢"),
    FEAR("😨"),
    JOY("😄"),
    INTEREST("😊"),
    SURPRISE("😮"),
    DISGUST("🤢"),
    SHAME("😳");

    companion object {
        fun find(emoji: String) = entries.first { it.emoji == emoji }

        val Saver = run {
            val nameKey = "emoji"
            val countryKey = "Country"
            mapSaver(
                save = { mapOf(nameKey to it.emoji) },
                restore = { PrimaryEmotion.find(nameKey) }
            )
        }
    }

}


class PrimaryEmotionsStore {
    val primaryEmotionsList = PrimaryEmotion.entries.toList()
}

@Composable
fun rememberPrimaryEmotionsStore() = remember {
    PrimaryEmotionsStore()
}