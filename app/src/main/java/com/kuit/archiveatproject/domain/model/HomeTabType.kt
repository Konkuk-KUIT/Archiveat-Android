package com.kuit.archiveatproject.domain.model

import android.graphics.Color
import androidx.compose.runtime.Composable
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

enum class HomeTabType {
    ALL, INSPIRATION, DEEP_DIVE, GROWTH, VIEW_EXPANSION;

    companion object {
        fun from(raw: String): HomeTabType =
            runCatching { valueOf(raw) }.getOrElse { ALL }
    }

    @Composable
    fun color(): androidx.compose.ui.graphics.Color = when (this) {
        ALL -> ArchiveatProjectTheme.colors.gray700
        INSPIRATION -> ArchiveatProjectTheme.colors.primary
        DEEP_DIVE -> ArchiveatProjectTheme.colors.sub_2
        GROWTH -> ArchiveatProjectTheme.colors.sub_1
        VIEW_EXPANSION -> ArchiveatProjectTheme.colors.sub_3
    }
}