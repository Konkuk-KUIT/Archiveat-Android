package com.kuit.archiveatproject.domain.model

enum class HomeTabType {
    ALL, INSPIRATION, DEEP_DIVE, GROWTH, VIEW_EXPANSION;

    companion object {
        fun from(raw: String): HomeTabType =
            runCatching { valueOf(raw) }.getOrElse { ALL }
    }
}