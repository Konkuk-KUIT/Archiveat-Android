package com.kuit.archiveatproject.presentation.home.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun HomeTabType.color(): Color = when (this) {
    HomeTabType.ALL -> ArchiveatProjectTheme.colors.gray700
    HomeTabType.INSPIRATION -> ArchiveatProjectTheme.colors.primary
    HomeTabType.DEEP_DIVE -> ArchiveatProjectTheme.colors.sub_2
    HomeTabType.GROWTH -> ArchiveatProjectTheme.colors.sub_1
    HomeTabType.VIEW_EXPANSION -> ArchiveatProjectTheme.colors.sub_3
}