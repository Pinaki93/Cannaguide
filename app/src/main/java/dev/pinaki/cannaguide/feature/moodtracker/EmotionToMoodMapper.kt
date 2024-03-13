package dev.pinaki.cannaguide.feature.moodtracker

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.pinaki.cannaguide.CannaguideApp
import dev.pinaki.cannaguide.R
import dev.pinaki.cannaguide.data.store.PrimaryEmotion

class EmotionToMoodMapper(
    private val application: Application = CannaguideApp.instance
) {
    private fun mapString(emotion: PrimaryEmotion): String {
        return when (emotion) {
            PrimaryEmotion.ANGER -> application.getString(R.string.anger_description)
            PrimaryEmotion.SADNESS -> application.getString(R.string.sadness_description)
            PrimaryEmotion.FEAR -> application.getString(R.string.fear_description)
            PrimaryEmotion.JOY -> application.getString(R.string.joy_description)
            PrimaryEmotion.INTEREST -> application.getString(R.string.interest_description)
            PrimaryEmotion.SURPRISE -> application.getString(R.string.surprise_description)
            PrimaryEmotion.DISGUST -> application.getString(R.string.disgust_description)
            PrimaryEmotion.SHAME -> application.getString(R.string.shame_description)
            PrimaryEmotion.BOREDOM -> application.getString(R.string.boredom_description)
        }
    }

    fun map(emotion: PrimaryEmotion): MoodItem {
        return MoodItem(emotion.emoji, mapString(emotion))
    }
}

@Composable
fun rememberEmotionToMoodMapper() = remember {
    EmotionToMoodMapper()
}
