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
    BOREDOM("\uD83D\uDE12"),
    SHAME("😳");

    companion object {
        fun find(emoji: String) = entries.first { it.emoji == emoji }
    }

}


class PrimaryEmotionsStore {
    val primaryEmotionsList = PrimaryEmotion.entries.toList()
}

@Composable
fun rememberPrimaryEmotionsStore() = remember {
    PrimaryEmotionsStore()
}