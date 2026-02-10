package com.kuit.archiveatproject.presentation.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicNewsletterDto
import com.kuit.archiveatproject.presentation.explore.component.ExploreTopicContentCard
import com.kuit.archiveatproject.presentation.explore.component.ExploreTopicFilterChip
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import java.time.Instant

@Composable
fun ExploreTopicDetailScreen(
    topicId: Long,
    topicName: String,
    newsletters: List<ExploreTopicNewsletterDto>,
    onBack: () -> Unit,
    onClickOutlink: (userNewsletterId: Long) -> Unit,
    onSearchSubmit: (query: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 1) 안읽음 -> 2) 읽음, 각각 최신 저장순(createdAt desc)
    val sorted = remember(newsletters) { newsletters.sortedForExplore() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        BackTopBar(
            title = topicName,
            onBack = onBack,
            height = 56,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 9.dp)
        ) {
            // TODO: 검색 컴포넌트로 변경
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ArchiveatProjectTheme.colors.gray50)
            )

            Spacer(Modifier.height(15.dp))

            // Filter Row (MVP: UI only)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ExploreTopicFilterChip(text = "시간", onClick = { /* MVP: no-op */ })
                ExploreTopicFilterChip(text = "유형", onClick = { /* MVP: no-op */ })
                ExploreTopicFilterChip(text = "저장일", onClick = { /* MVP: no-op */ })
            }
        }

        // 콘텐츠 그리드. 여기만 스크롤
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 24.dp, top = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = sorted,
                key = { it.userNewsletterId },
            ) { item ->
                ExploreTopicContentCard(
                    title = item.title,
                    thumbnailUrl = item.thumbnailUrl,
                    isRead = item.isRead,
                    onClickCard = { onClickOutlink(item.userNewsletterId) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

/** 정렬: 안읽음 -> 읽음, 각 그룹 내부는 createdAt desc(null은 가장 오래된 취급) */
private fun List<ExploreTopicNewsletterDto>.sortedForExplore(): List<ExploreTopicNewsletterDto> {
    fun ExploreTopicNewsletterDto.createdAtInstantOrMin(): Instant {
        val raw = createdAt ?: return Instant.MIN
        return runCatching { Instant.parse(raw) }.getOrDefault(Instant.MIN)
    }

    val (read, unread) = this.partition { it.isRead } // read=true가 먼저 나오므로 주의
    val unreadSorted = unread.sortedByDescending { it.createdAtInstantOrMin() }
    val readSorted = read.sortedByDescending { it.createdAtInstantOrMin() }

    return unreadSorted + readSorted
}

@Preview(showBackground = true)
@Composable
private fun Preview_ExploreTopicDetailScreen_Default() {
    ArchiveatProjectTheme {
        ExploreTopicDetailScreen(
            topicId = 1L,
            topicName = "테크",
            newsletters = listOf(
                ExploreTopicNewsletterDto(
                    userNewsletterId = 101L,
                    title = "디지털 시대를 설계하다 · 컴퓨터가 바꾸는 일상의 풍경",
                    thumbnailUrl = "https://picsum.photos/400/300?1",
                    isRead = false,
                    createdAt = Instant.now().toString()
                ),
                ExploreTopicNewsletterDto(
                    userNewsletterId = 102L,
                    title = "알고리즘을 이해하다 · 추천 시스템 뒤에 숨은 계산법",
                    thumbnailUrl = "https://picsum.photos/400/300?2",
                    isRead = false,
                    createdAt = Instant.now().minusSeconds(3600).toString()
                ),
                ExploreTopicNewsletterDto(
                    userNewsletterId = 103L,
                    title = "데이터의 바다를 항해하다 · 빅데이터 분석으로 본 트렌드",
                    thumbnailUrl = null,
                    isRead = true,
                    createdAt = Instant.now().minusSeconds(7200).toString()
                ),
                ExploreTopicNewsletterDto(
                    userNewsletterId = 104L,
                    title = "인공지능과 함께 일하다 · 생산성을 높이는 스마트 워크. 인공지능과 함께 일하다 · 생산성을 높이는 스마트 워크",
                    thumbnailUrl = "https://picsum.photos/400/300?4",
                    isRead = true,
                    createdAt = Instant.now().minusSeconds(9000).toString()
                ),
            ),
            onBack = {},
            onClickOutlink = {},
            onSearchSubmit = {},
        )
    }
}