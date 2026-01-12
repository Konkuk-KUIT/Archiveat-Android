package com.kuit.archiveatproject.presentation.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.TopLogoBar
import com.kuit.archiveatproject.domain.model.HomeCardType
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.home.component.GreetingBar
import com.kuit.archiveatproject.presentation.home.component.HomeCategoryTabBar
import com.kuit.archiveatproject.presentation.home.component.HomeContentCardCarousel
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeTabUiModel

//임시 변수(서버 구현 후 삭제 예정)
private val mockTabs = listOf(
    HomeTabUiModel(HomeTabType.ALL, "전체", "수집한 자료를 기반으로 발행된, 나만의 지식 뉴스레터"),
    HomeTabUiModel(HomeTabType.INSPIRATION, "영감수집", "잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트"),
    HomeTabUiModel(HomeTabType.DEEP_DIVE, "집중탐구", "관심 주제를 깊이 파고들어, 온전히 내 것으로 만드는 시간"),
    HomeTabUiModel(HomeTabType.GROWTH, "성장한입", "잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트"),
    HomeTabUiModel(HomeTabType.VIEW_EXPANSION, "관점확장", "생각의 크기를 키워주는 깊이 있는 통찰"),
)

private val mockHomeCards = run {
    val urls1 = listOf("https://picsum.photos/id/10/800/600")
    val urls2 = listOf(
        "https://picsum.photos/id/11/800/600",
        "https://picsum.photos/id/12/800/600"
    )

    listOf(
        HomeContentCardUiModel(
            archiveId = 1L,
            tabType = HomeTabType.ALL,
            tabLabel = "전체",
            cardType = HomeCardType.AI_SUMMARY,
            title = "1장 이미지 카드 예시",
            imageUrls = urls1
        ),
        HomeContentCardUiModel(
            archiveId = 2L,
            tabType = HomeTabType.INSPIRATION,
            tabLabel = "영감수집",
            cardType = HomeCardType.COLLECTION,
            title = "2장 이미지 카드 예시",
            imageUrls = urls2
        ),
        HomeContentCardUiModel(
            archiveId = 3L,
            tabType = HomeTabType.DEEP_DIVE,
            tabLabel = "집중탐구",
            cardType = HomeCardType.COLLECTION,
            title = "다시 1장 케이스",
            imageUrls = urls1
        )
    )
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    var selectedTab by remember { mutableStateOf(HomeTabType.ALL) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // 고정 영역
        TopLogoBar()

        GreetingBar(
            "archiveat",
            "좋은 아침이에요!",
            "오늘도 한 걸음 성장해볼까요?"
        )

        HomeCategoryTabBar(
            tabs = mockTabs,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(Modifier.height(27.dp))

        HomeContentCardCarousel(
            cards = mockHomeCards,
            modifier = Modifier.fillMaxWidth(),
            onCenterCardClick = { card ->
                // TODO: 상세 화면 이동
                // navController.navigate("archive/${card.archiveId}")
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun HomeScreenPreview(){
    HomeScreen()
}