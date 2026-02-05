package com.kuit.archiveatproject.core.component.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

sealed class TagVariant {
    data class Tab(val type: HomeTabType) : TagVariant()
    data class CardType(val type: HomeCardType) : TagVariant()
    object Custom : TagVariant()
}

data class TagColors(
    val text: Color,
    val background: Color
)

@Composable
fun tagColorsFor(variant: TagVariant): TagColors {
    val c = ArchiveatProjectTheme.colors

    return when (variant) {
        is TagVariant.Tab -> when (variant.type) {
            HomeTabType.ALL -> TagColors(c.white, c.deepIndigo)
            HomeTabType.INSPIRATION -> TagColors(c.white, c.primary)
            HomeTabType.DEEP_DIVE -> TagColors(c.white, c.sub_2)
            HomeTabType.GROWTH -> TagColors(c.white, c.sub_1)
            HomeTabType.VIEW_EXPANSION -> TagColors(c.white, c.sub_3)
        }

        is TagVariant.CardType -> when (variant.type) {
            HomeCardType.AI_SUMMARY -> TagColors(c.white, c.deepIndigo)
            HomeCardType.COLLECTION -> TagColors(c.white, c.deepIndigo)
            // 타입 더 생기면 여기 추가
        }

        TagVariant.Custom -> TagColors(c.gray800, c.gray50)
    }
}