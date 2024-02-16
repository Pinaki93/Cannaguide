package dev.pinaki.cannaguide.di

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun rememberCommonDateFormatter() = remember {
    SimpleDateFormat("dd MMM yyyy")
}

@SuppressLint("SimpleDateFormat")
@Composable
fun rememberCommonTimeFormatter() = remember {
    SimpleDateFormat("hh:mm a")
}