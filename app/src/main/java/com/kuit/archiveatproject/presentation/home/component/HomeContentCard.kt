package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.domain.model.HomeCardType
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun HomeContentCard(
    card: HomeContentCardUiModel,
    // TODO: 서버 문의 - subtitle, contentSnippet
    subtitle: String,       // "저장한 'Topic' 아티클 요약" / ... <- 서버로부터 Topic 받기
    contentSnippet: String, // 아래 요약문 <- 삭제 요청 중(?)
    modifier: Modifier = Modifier,
    onClick: (HomeContentCardUiModel) -> Unit = {},
) {
    val shape = RoundedCornerShape(30.dp)

    Column(
        modifier = modifier
            .clip(shape)
            .background(ArchiveatProjectTheme.colors.white)
            .clickable { onClick(card) }
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = card.thumbnailUrl,
            contentDescription = null, // 서버에서 받아와야할 듯
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(268f / 174f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .padding(bottom = 2.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                TextTag(
                    text = card.tabLabel,
                    variant = TagVariant.Tab(type = card.tabType),
                )
                TextTag(
                    text = card.cardType.label,
                    variant = TagVariant.CardType(type = card.cardType)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = subtitle,
                style = ArchiveatProjectTheme.typography.Caption_semibold,
                color = ArchiveatProjectTheme.colors.gray900,
                minLines = 1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(11.dp))

            Text(
                text = card.title,
                style = ArchiveatProjectTheme.typography.Subhead_1_bold,
                color = ArchiveatProjectTheme.colors.black,
                minLines = 1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = contentSnippet.orEmpty(),
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = ArchiveatProjectTheme.typography.Body_1_medium,
                color = ArchiveatProjectTheme.colors.gray800
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun HomeContentCardPrev() {
    LazyColumn {
        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 1,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "2025 UI 디자인 트렌드: 글래스모피즘의 귀환",
                    thumbnailUrl = "https://picsum.photos/id/1/200/300"
                ),
                subtitle = "저장한 'TechCrunch' 아티클 요약",
                contentSnippet = "애플 비전 프로 출시 이후 다시 떠오르는 투명한 UI 디자인. 그 핵심 요소 3가지와 실제 적용 사례를 빠르게 짚어드립니다. 애플 비전 프로 출시 이후 다시 떠오르는 투명한 UI 디자인. 그 핵심 요소 3가지와 실제 적용 사례를 빠르게 짚어드립니다.",
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }
        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 1,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "2025 UI 디자인 트렌드",
                    thumbnailUrl = "https://picsum.photos/id/1/200/300"
                ),
                subtitle = "저장한 'TechCrunch' 아티클 요약",
                contentSnippet = "애플 비전 프로 출시 이후 다시 떠오르는 투명한 UI 디자인. 그 핵심 요소 3가지와 실제 적용 사례를 빠르게 짚어드립니다. 애플 비전 프로 출시 이후 다시 떠오르는 투명한 UI 디자인. 그 핵심 요소 3가지와 실제 적용 사례를 빠르게 짚어드립니다.",
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }
        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 1,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "2025 UI 디자인 트렌드: 글래스모피즘의 귀환2025 UI 디자인 트렌드: 글래스모피즘의 귀환",
                    thumbnailUrl = "https://picsum.photos/id/1/200/300"
                ),
                subtitle = "저장한 'TechCrunch' 아티클 요약",
                contentSnippet = "애플 비전 프로 출시 이후 다시 떠오르는 투명한 UI 디자인. 그 핵심 요소 3가지와 실제 적용 사례를 빠르게 짚어드립니다.",
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }
        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 1,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "2025 UI 디자인 트렌드: 글래스모피즘의 귀환",
                    thumbnailUrl = "https://picsum.photos/id/1/200/300"
                ),
                subtitle = "저장한 'TechCrunch' 아티클 요약",
                contentSnippet = "애플 비전 프로 출시 이후 다시 떠오르는 투명한 UI 디자인. 그 핵심 요소 3가지와 실제 적용 사례를 빠르게 짚어드립니다.",
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                onClick = {}
            )
        }
    }
}