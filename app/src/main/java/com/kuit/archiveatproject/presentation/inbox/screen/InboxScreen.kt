package com.kuit.archiveatproject.presentation.inbox.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.domain.entity.Inbox
import com.kuit.archiveatproject.domain.entity.InboxCategory
import com.kuit.archiveatproject.domain.entity.InboxDateGroup
import com.kuit.archiveatproject.domain.entity.InboxItem
import com.kuit.archiveatproject.domain.entity.InboxTopic
import com.kuit.archiveatproject.domain.entity.LlmStatus
import com.kuit.archiveatproject.presentation.inbox.component.InboxDateHeader
import com.kuit.archiveatproject.presentation.inbox.component.InboxItemComponent
import com.kuit.archiveatproject.presentation.inbox.util.InboxFormatters
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun InboxScreen(
    inbox: Inbox,
    onBackToExploreFirstDepth: () -> Unit,  // 탐색 탭(1st depth)로 이동
    onDelete: (Long) -> Unit,               // newsletterId
    onOpenOriginal: (String) -> Unit,       // contentUrl
    onClickEdit: (InboxItem) -> Unit,       // 수정 바텀 시트 호출
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = ArchiveatProjectTheme.colors.white
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            BackTopBar(
                title = "방금 담은 지식",
                onBack = onBackToExploreFirstDepth,
                height = 56
            )

            Text(
                text = "AI 분류 결과를 수정하고 원본을 볼 수 있어요.",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray600
            )

            val groups = inbox.inbox.sortedByDescending { it.date } // "yyyy-MM-dd"라면 문자열 정렬도 OK

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ArchiveatProjectTheme.colors.gray50)
                    .padding(horizontal = 20.dp),
            ) {
                groups.forEach { group ->
                    item(key = "header-${group.date}") {
                        InboxDateHeader(
                            label = InboxFormatters.dateHeaderLabel(group.date),
                            count = group.count
                        )
                    }

                    itemsIndexed(
                        items = group.items,
                        key = { _, item -> item.newsletterId }
                    ) { index, item ->
                        InboxItemComponent(
                            item = item,
                            onDelete = onDelete,
                            onOpenOriginal = onOpenOriginal,
                            onClickEdit = onClickEdit
                        )
                        if (index != group.items.lastIndex) {
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
                item { Spacer(Modifier.height(14.dp)) }
            }
        }
    }
}

// // // // //

private fun sampleInbox(): Inbox {
    return Inbox(
        inbox = listOf(
            InboxDateGroup(
                date = "2026-01-18",
                count = 2,
                items = listOf(
                    // 로딩(웹사이트 + 오후 02:30)
                    InboxItem(
                        newsletterId = 101,
                        llmStatus = LlmStatus.RUNNING,
                        contentUrl = "https://n.news.naver.com/article/028/0002787393?cds=news_media_pc",
                        domainName = null,
                        createdAt = "2026-01-18T14:30:00+09:00",
                        category = null,
                        topic = null
                    ),
                    // 완료
                    InboxItem(
                        newsletterId = 102,
                        llmStatus = LlmStatus.DONE,
                        contentUrl = "“돈도 기업도 한국을 떠난다” 2026년 한국 경제가 진짜 무서운 이유 (김정호 교수)",
                        domainName = "Youtube",
                        createdAt = "2026-01-18T14:30:00+09:00",
                        category = InboxCategory(id = 1, name = "경제"),
                        topic = InboxTopic(id = 1, name = "경제전망")
                    )
                )
            ),
            InboxDateGroup(
                date = "2026-01-26",
                count = 1,
                items = listOf(
                    InboxItem(
                        newsletterId = 104,
                        llmStatus = LlmStatus.PENDING,
                        contentUrl = "https://example.com/waiting",
                        domainName = null,
                        createdAt = null,
                        category = null,
                        topic = null
                    )
                )
            ),
            InboxDateGroup(
                date = "2026-01-10",
                count = 1,
                items = listOf(
                    InboxItem(
                        newsletterId = 105,
                        llmStatus = LlmStatus.FAILED,
                        contentUrl = "https://example.com/waiting",
                        domainName = null,
                        createdAt = null,
                        category = null,
                        topic = null
                    )
                )
            ),
            InboxDateGroup(
                date = "2026-01-25",
                count = 1,
                items = listOf(
                    InboxItem(
                        newsletterId = 106,
                        llmStatus = LlmStatus.DONE,
                        contentUrl = "https://example.com/waiting",
                        domainName = null,
                        createdAt = null,
                        category = null,
                        topic = null
                    )
                )
            )
        )
    )
}

@Preview(name = "InboxScreen", showBackground = true)
@Composable
private fun InboxScreenPreview() {
    ArchiveatProjectTheme {
        InboxScreen(
            inbox = sampleInbox(),
            onBackToExploreFirstDepth = {},
            onDelete = {},
            onOpenOriginal = {},
            onClickEdit = {},
        )
    }
}