package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionComponentUiModel
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionTopBar
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewsletterDetailsCollectionScreen(
    userName: String,
    categoryLabel: String,  // 경제
    monthLabel: String,     // 8월
    items: List<CollectionComponentUiModel>,
    onBack: () -> Unit,
    onClickItem: (Long) -> Unit,
    onToggleChecked: (Long, Boolean) -> Unit = { _, _ -> }, // (서버/VM 연동 대비: id, newChecked)
    modifier: Modifier = Modifier,
) {
    // 임시: 나중에 VM 붙이면 삭제
    val stateItems = remember(items) {
        mutableStateListOf<CollectionComponentUiModel>().apply { addAll(items) }
    }

    val collectedCount = stateItems.size
    val progressTotal = collectedCount.coerceAtLeast(1)
    val progressCurrent = stateItems.count { it.isChecked }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        BackTopBar(
            onBack = onBack,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        CollectionTopBar(
            userName = userName,
            categoryLabel = categoryLabel,
            collectedCount = collectedCount,
            monthLabel = monthLabel,
            progressCurrent = progressCurrent,
            progressTotal = progressTotal,
            modifier = Modifier
        )

        Box(modifier = Modifier.weight(1f)
            .background(ArchiveatProjectTheme.colors.gray50)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp, end = 20.dp,
                    top = 14.dp, bottom = 27.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = stateItems,
                    key = { item: CollectionComponentUiModel -> item.id }
                ) { model: CollectionComponentUiModel ->
                    CollectionComponent(
                        model = model,
                        onClick = { onClickItem(model.id) },
                        onToggleChecked = { id ->
                            val idx = stateItems.indexOfFirst { it.id == id }
                            if (idx != -1) {
                                val newChecked = !stateItems[idx].isChecked
                                stateItems[idx] = stateItems[idx].copy(isChecked = newChecked)
                                onToggleChecked(id, newChecked)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsletterDetailsCollectionScreenPreview() {
    ArchiveatProjectTheme {
        NewsletterDetailsCollectionScreen(
            userName = "OO",
            categoryLabel = "경제",
            monthLabel = "8월",
            items = listOf(
                CollectionComponentUiModel(
                    id = 1L,
                    categoryLabel = "경제정책",
                    sourceIcon = "", // 프리뷰에서는 빈 값이면 ImageTag가 fallback/깨질 수 있어서 주의
                    sourceLabel = "Youtube",
                    minutesLabel = "30분",
                    thumbnailUrl = "", // 빈 값이면 placeholder 박스
                    title = "\"돈도 기업도 한국을 떠났다\" 2026년 한국 경제가 진짜 무서운 이유 (김정호 교수)?",
                    subtitle = "제조업 무너짐 심각해진 분위기",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 2L,
                    categoryLabel = "가상자산",
                    sourceIcon = "",
                    sourceLabel = "다음뉴스",
                    minutesLabel = "35분",
                    thumbnailUrl = "",
                    title = "비트코인 '4년 주기론' 끝나나…'업토버' 사라진 이유는 [한경 코알라]",
                    subtitle = "비트코인 팔 때 참고할 것",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 3L,
                    categoryLabel = "주식",
                    sourceIcon = "",
                    sourceLabel = "브런치",
                    minutesLabel = "5분",
                    thumbnailUrl = "",
                    title = "같은 3000만원 수익, 세금은 제각각?",
                    subtitle = "같은 수익 다른 세금, 뒤틀린 자본시장 과세",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 4L,
                    categoryLabel = "국제경제",
                    sourceIcon = "",
                    sourceLabel = "Instagram",
                    minutesLabel = "3분",
                    thumbnailUrl = "",
                    title = "중동전쟁 오일쇼크! (~24.10 진행상황)",
                    subtitle = "제5차 중동전쟁 발발 위기?",
                    isChecked = false
                )
            ),
            onBack = {},
            onClickItem = {},
            onToggleChecked = { _, _ -> }
        )
    }
}