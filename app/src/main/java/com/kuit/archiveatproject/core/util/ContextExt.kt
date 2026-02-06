package com.kuit.archiveatproject.core.util

import android.content.Context
import android.widget.Toast
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun Context.toast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun toRelativeDateText(
    lastViewedAt: String,
    serverTimestamp: String
): String {
    val viewedDate = LocalDate.parse(lastViewedAt) // 2026-01-22
    val serverDate = LocalDate.parse(serverTimestamp.substring(0, 10)) // 2026-01-25

    val diffDays = ChronoUnit.DAYS.between(viewedDate, serverDate)

    return when {
        diffDays <= 0 -> "오늘"
        diffDays == 1L -> "어제"
        else -> "${diffDays}일 전"
    }
}