package dev.pinaki.cannaguide.ui.component

import java.util.Calendar

fun interface DatePickerDialogValidator {
    fun validate(date: Long): Boolean

    companion object {
        val avoidFutureDates = DatePickerDialogValidator { timeInMillis ->
            val date = Calendar.getInstance()
                .get(Calendar.DATE)

            val selectedDate = Calendar.getInstance()
                .apply {
                    setTimeInMillis(timeInMillis)
                }[Calendar.DATE]

            selectedDate <= date
        }
    }
}