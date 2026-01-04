package com.kuit.archiveatproject.domain.model

enum class HomeCardType(val label: String) {
    AI_SUMMARY("AI 요약"),
    COLLECTION("컬렉션");

    companion object {
        fun fromLabel(label: String): HomeCardType =
            entries.firstOrNull { it.label == label.trim() } ?: AI_SUMMARY
    }
}