package com.kuit.archiveatproject.core.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun TextTag(
    text: String,
    variant: TagVariant,
    modifier: Modifier = Modifier,
    colors: TagColors? = null, // 필요하면 직접 색 지정(override)
    style: TextStyle = ArchiveatProjectTheme.typography.Body_1_semibold
) {
    val resolved = colors ?: tagColorsFor(variant)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(44.79.dp))
            .background(resolved.background)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = resolved.text,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun TextTagPrev() {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            TextTag(
                text = "영감수집",
                variant = TagVariant.Tab(HomeTabType.INSPIRATION)
            )
            TextTag(
                text = "집중탐구",
                variant = TagVariant.Tab(HomeTabType.DEEP_DIVE)
            )
            TextTag(
                text = "성장한입",
                variant = TagVariant.Tab(HomeTabType.GROWTH)
            )
            TextTag(
                text = "관점확장",
                variant = TagVariant.Tab(HomeTabType.VIEW_EXPANSION)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            TextTag(
                text = "AI 요약",
                variant = TagVariant.CardType(HomeCardType.AI_SUMMARY)
            )
            TextTag(
                text = "컬렉션",
                variant = TagVariant.CardType(HomeCardType.COLLECTION)
            )
            TextTag(
                text = "미국 주식",
                variant = TagVariant.Custom
            )
        }
    }
}