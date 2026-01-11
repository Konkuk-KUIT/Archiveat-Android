package com.kuit.archiveatproject.domain.model

import android.graphics.Color
import androidx.compose.runtime.Composable
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

enum class HomeTabType(
    val label: String,
    val subMessage: String
) {
    ALL(
        label = "전체",
        subMessage = "흩어진 모든 지식을 한눈에"
    ),
    INSPIRATION(
        label = "영감수집",
        subMessage = "잠깐의 틈을 채워줄 인사이트"
    ),
    DEEP_DIVE(
        label = "집중탐구",
        subMessage = "관심 주제를 깊이 파고들어, 온전히 내 것으로 만드는 시간"
    ),
    GROWTH(
        label = "성장한입",
        subMessage = "잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트"
    ),
    VIEW_EXPANSION(
        label = "관점확장",
        subMessage = "생각의 크기를 키워주는 깊이 있는 통찰"
    );

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