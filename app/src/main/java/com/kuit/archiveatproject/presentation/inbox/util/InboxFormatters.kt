package com.kuit.archiveatproject.presentation.inbox.util

import java.time.Duration
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object InboxFormatters {
    /** dateString: "2026-01-18" */
    fun dateHeaderLabel(dateString: String, zoneId: ZoneId = ZoneId.systemDefault()): String {
        val date = runCatching { LocalDate.parse(dateString) }.getOrNull() ?: return dateString
        val today = LocalDate.now(zoneId)

        return when (date) {
            today -> "Today"
            today.minusDays(1) -> "Yesterday"
            else -> date.format(DateTimeFormatter.ofPattern("MM/dd"))
        }
    }

    /** createdAt: "2026-01-18T09:12:33+09:00" -> "오후 02:30" */
    fun ampmTime(createdAt: String?, zoneId: ZoneId = ZoneId.systemDefault()): String? {
        if (createdAt.isNullOrBlank()) return null
        val odt = runCatching { OffsetDateTime.parse(createdAt) }.getOrNull() ?: return null
        val zdt = odt.atZoneSameInstant(zoneId)
        return zdt.format(DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREAN))
    }

    /**
     * DONE 상태 시간 표현:
     * 0~1분: 방금 전
     * 1~30분: N분 전
     * 이외: 오전/오후 HH:MM
     */
    fun doneTimeLabel(createdAt: String?, zoneId: ZoneId = ZoneId.systemDefault()): String? {
        if (createdAt.isNullOrBlank()) return null
        val odt = runCatching { OffsetDateTime.parse(createdAt) }.getOrNull() ?: return null

        val now = ZonedDateTime.now(zoneId)
        val saved = odt.atZoneSameInstant(zoneId)
        val minutes = Duration.between(saved, now).toMinutes()

        return when {
            minutes <= 1 -> "방금 전"
            minutes in 2..30 -> "${minutes}분 전"
            else -> saved.format(DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREAN))
        }
    }
}