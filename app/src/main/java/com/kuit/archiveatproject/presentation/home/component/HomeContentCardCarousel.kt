package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.domain.model.HomeCardType
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.absoluteValue

@Composable
fun HomeContentCardCarousel(
    cards: List<HomeContentCardUiModel>,
    modifier: Modifier = Modifier,
    autoScroll: Boolean = true,
    autoScrollIntervalMs: Long = 3500L,     // 몇 ms마다 다음 카드로 갈지
    autoScrollResumeDelayMs: Long = 1200L,  // 사용자가 드래그하면 잠깐 멈췄다가 재개
    onCenterCardClick: (HomeContentCardUiModel) -> Unit = {}, // 가운데 카드 클릭 시 상세 이동
) {
    if (cards.isEmpty()) return // 0개면 Pager X -> 리턴

    // 디자인 값
    val cardWidth = 268.dp // 카드 폭
    val cardHeight = 395.dp // 카드 높이(중앙)
    val sideHeight = 364.dp // 옆 카드 높이 목표
    val minScaleY = sideHeight.value / cardHeight.value // 364/395
    val shape = RoundedCornerShape(30.dp) // 카드 모서리

    // 그림자 파라미터
    val shadowBlur = 3.dp
    val shadowOffsetY = 3.dp
    val shadowPad = shadowBlur + 2.dp  // blur보다 살짝 크게(잘림 방지 여백)

    // 그림자 포함 프레임 크기
    val frameWidth = cardWidth + shadowPad * 2
    val frameHeight = cardHeight + shadowPad * 2 + shadowOffsetY

    val pagerHeight = frameHeight

    // 중앙 정렬: 화면 폭으로 좌/우 padding 계산
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp // 화면 폭 dp
    val sidePadding = ((screenWidth - frameWidth) / 2).coerceAtLeast(0.dp)

    // Int.MAX_VALUE -> 페이지, 실제 데이터: page % cards.size
    val startPage = remember(cards.size) {  // 리스트 크기 바뀌면 다시 계산
        val mid = Int.MAX_VALUE / 2
        mid - (mid % cards.size)    // mid를 cards.size로 나눠떨어지게 맞춰 index 깔끔하게
    }

    val pagerState = rememberPagerState(    // Pager 상태 생성
        initialPage = startPage,            // 시작 페이지
        pageCount = { Int.MAX_VALUE }       // 페이지 무한에 가깝게
    )

    val scope = rememberCoroutineScope()    // animateScrollToPage 위한 코루틴 스코프
    val pagerMutex = remember { MutatorMutex() } // 스크롤 애니메이션 겹침 방지
    // 스크롤 요청을 마지막 1개만 유지 (연타해도 backlog X)
    val scrollRequests =
        remember { Channel<Pair<Int, MutatePriority>>(capacity = Channel.CONFLATED) }
    // 스크롤 애니메이션은 이 소비자 코루틴 하나만 실행
    LaunchedEffect(Unit) {
        for ((targetPage, priority) in scrollRequests) {
            try {
                pagerMutex.mutate(priority) {
                    // 이미 같은 페이지면 스킵
                    if (pagerState.currentPage != targetPage) {
                        pagerState.animateScrollToPage(targetPage)
                    }
                }
            } catch (e: CancellationException) {
                // Composable이 사라져서 취소된 거면 코루틴도 종료
                if (!currentCoroutineContext().isActive) throw e
                // 그 외 취소는 무시하고 다음 요청 계속 처리
            } catch (t: Throwable) {
                // 혹시 모를 예외
                // Log.e("Carousel", "scroll consumer crashed", t)
            }
        }
    }

    // 자동 회전
    LaunchedEffect(autoScroll, cards.size) { // 자동회전 ON/OFF or 리스트 크기 바뀌면 재시작
        if (!autoScroll) return@LaunchedEffect // OFF면 아무 것도 안 함
        while (true) {
            delay(autoScrollIntervalMs)  // 일정 시간 대기
            if (pagerState.isScrollInProgress) {    // 사용자가 드래그 중이면 잠깐 쉬었다가 다음 루프
                delay(autoScrollResumeDelayMs)
                continue
            }
            // 다음 페이지로 부드럽게 이동, 자동 회전은 Default 우선순위 -> 사용자 입력이 들어오면 밀림
            scrollRequests.trySend((pagerState.currentPage + 1) to MutatePriority.Default)
        }
    }

    // 캐러셀
    HorizontalPager(
        state = pagerState, // Pager 상태 연결
        modifier = modifier
            .fillMaxWidth()
            .height(pagerHeight),
        pageSize = PageSize.Fixed(frameWidth), // 페이지 폭=프레임 폭
        contentPadding = PaddingValues(horizontal = sidePadding), // 좌우 padding -> 카드가 화면 중앙에
        pageSpacing = 12.dp, // 카드 사이 간격
        beyondViewportPageCount = 2 // 양옆 2장 미리 compose -> 스크롤/애니메이션 부드러움
    ) { page ->

        // 실제 데이터 인덱스(1~N 반복)
        val realIndex = page % cards.size // page는 무한, 데이터는 0..N-1 반복
        val card = cards[realIndex] // 실제 카드

        // 현재 페이지에서 얼마나 떨어져있는지(0=중앙, 1=옆)
        val rawOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
        val t = rawOffset.coerceIn(0f, 1f)

        // 중앙=1, 옆=0 - 부드럽게
        val centerStrength = smoothStep(1f - t)
        // 양옆 alpha 0.6, 중앙 1.0
        val alpha = 0.6f + 0.4f * centerStrength

        // 양옆 높이 축소(395->364)
        val scaleY = minScaleY + (1f - minScaleY) * centerStrength

        // 양옆 그레이 스케일
        val grayFilter: ColorFilter? =
            if (t <= 0f) null // 중앙
            else { // 옆으로 갈수록 setToSaturation 값이 줄어듦
                val m = ColorMatrix().apply { setToSaturation(1f - t) }
                ColorFilter.colorMatrix(m)
            }

        // 카드 배경색: 중앙 white, 옆 gray100
        val bgT = t.coerceIn(0f, 1f)
        val containerColor = lerp(
            ArchiveatProjectTheme.colors.white,
            ArchiveatProjectTheme.colors.gray100,
            bgT
        )

        // 그림자: 중앙에서 강하고 옆에서 0
        val shadowStrength = centerStrength

        // 탭 동작: 옆 카드 탭하면 그 카드로 이동 / 중앙 탭이면 상세
        val onTap = Modifier.clickable {
            if (page != pagerState.currentPage) {
                scrollRequests.trySend(page to MutatePriority.UserInput)
            } else {
                onCenterCardClick(card)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(pagerHeight),
            contentAlignment = Alignment.TopCenter
        ) {
            CarouselCardFrame(
                modifier = Modifier
                    .size(frameWidth, frameHeight) // 기본 카드 크기 고정
                    .then(onTap), // 탭 동작 부여
                shape = shape,
                alpha = alpha,
                scaleY = scaleY,
                shadowStrength = shadowStrength,
                grayscale = grayFilter,
                shadowPad = shadowPad,
                shadowBlur = shadowBlur,
                shadowOffsetY = shadowOffsetY,
            ) {
                HomeContentCard(
                    card = card,
                    subtitle = "저장한 'Topic' 아티클 요약", // TODO: 서버에서 ...
                    contentSnippet = "간단한 설명 요약문. 간단한 설명 요약문. 간단한 설명 요약문. 간단한 설명 요약문.", // TODO: 서버에서 ...
                    modifier = Modifier.fillMaxSize(),
                    isClickable = false, // 바깥 onTap이 담당 -> 내부 클릭은 사용 안 할 듯
                    containerColor = containerColor,
                    onClick = {}
                )
            }
        }
    }

}

// 카드 프레임(그림자 + 그레이 + alpha + scaleY)
@Composable
private fun CarouselCardFrame(
    modifier: Modifier,
    shape: RoundedCornerShape,
    alpha: Float, // 중앙=1, 옆=0.6
    scaleY: Float, // 중앙=1, 옆=364/395
    shadowStrength: Float, // 중앙=1, 옆=0
    grayscale: ColorFilter?,
    shadowPad: Dp,
    shadowBlur: Dp,
    shadowOffsetY: Dp,
    content: @Composable () -> Unit // 내부 컨텐츠(HomeContentCard)
) {
    val shadowAlpha = 0.20f * shadowStrength
    val corner = 30.dp
    // 실제 카드 크기
    val cardWidth = 268.dp
    val cardHeight = 395.dp

    Box(
        modifier = modifier
            .graphicsLayer {
                this.alpha = alpha // 전체 알파 적용
                this.scaleY = scaleY // 높이 축소 적용
                transformOrigin = TransformOrigin.Center // 가운데 기준 축소
                clip = false // 그림자 퍼짐 잘리기 X
            }
    ) {
        Box( // 그림자
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    val left = shadowPad.toPx()
                    val top = shadowPad.toPx()
                    val right = left + cardWidth.toPx()
                    val bottom = top + cardHeight.toPx()

                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            this.color = android.graphics.Color.TRANSPARENT
                            setShadowLayer(
                                shadowBlur.toPx(),
                                0f,
                                shadowOffsetY.toPx(),
                                Color.Black.copy(alpha = shadowAlpha).toArgb()
                            )
                        }
                    }

                    drawIntoCanvas { canvas ->
                        canvas.drawRoundRect(
                            left, top, right, bottom,
                            corner.toPx(), corner.toPx(),
                            paint
                        )
                    }
                }
        )

        Surface(
            modifier = Modifier
                .offset(x = shadowPad, y = shadowPad) // 그림자와 동일 위치
                .size(cardWidth, cardHeight)
                .clip(shape)
                .then(if (grayscale != null) Modifier.colorMatrixLayer(grayscale) else Modifier),
            shape = shape,
            color = Color.Transparent,
            shadowElevation = 0.dp,
            tonalElevation = 0.dp
        ) {
            content() // HomeContentCard 렌더
        }
    }
}

private fun smoothStep(x: Float): Float {
    val t = x.coerceIn(0f, 1f)
    return t * t * (3f - 2f * t)
}

private fun Modifier.colorMatrixLayer(filter: ColorFilter): Modifier =
    this.drawWithContent {
        val p = Paint().apply { colorFilter = filter }
        drawIntoCanvas { canvas ->
            canvas.saveLayer(size.toRect(), p)
            drawContent()
            canvas.restore()
        }
    }

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF7F7F7
)
@Composable
fun HomeContentCardCarouselPrev() {
    val dummyCards = listOf(
        HomeContentCardUiModel(
            archiveId = 1,
            tabType = HomeTabType.INSPIRATION,
            tabLabel = "영감수집",
            cardType = HomeCardType.AI_SUMMARY,
            title = "2025 UI 디자인 트렌드: 글래스모피즘의 귀환",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
        HomeContentCardUiModel(
            archiveId = 2,
            tabType = HomeTabType.DEEP_DIVE,
            tabLabel = "집중탐구",
            cardType = HomeCardType.AI_SUMMARY,
            title = "AI 에이전트, 검색을 넘어 '행동'으로",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
        HomeContentCardUiModel(
            archiveId = 3,
            tabType = HomeTabType.GROWTH,
            tabLabel = "성장한입",
            cardType = HomeCardType.AI_SUMMARY,
            title = "사회초년생을 위한 ETF 투자 바이블",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
        HomeContentCardUiModel(
            archiveId = 4,
            tabType = HomeTabType.VIEW_EXPANSION,
            tabLabel = "관점확장",
            cardType = HomeCardType.AI_SUMMARY,
            title = "알고리즘을 이해하다 · 추천 시스템 뒤에 숨은 계산법",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
        HomeContentCardUiModel(
            archiveId = 5,
            tabType = HomeTabType.INSPIRATION,
            tabLabel = "영감수집",
            cardType = HomeCardType.AI_SUMMARY,
            title = "데이터의 바다를 항해하다 · 빅데이터 분석으로 본 트렌드",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
        HomeContentCardUiModel(
            archiveId = 6,
            tabType = HomeTabType.DEEP_DIVE,
            tabLabel = "집중탐구",
            cardType = HomeCardType.AI_SUMMARY,
            title = "\"돈도 기업도 한국을 떠났다\" 2026년 한국 경제가 진짜 무서운 이유 (김정호 교수)?",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
        HomeContentCardUiModel(
            archiveId = 7,
            tabType = HomeTabType.GROWTH,
            tabLabel = "성장한입",
            cardType = HomeCardType.AI_SUMMARY,
            title = "같은 3000만원 수익, 세금은 제각각?",
            thumbnailUrl = "https://picsum.photos/200/300"
        ),
    )
    Column(
        modifier = Modifier.padding(vertical = 100.dp)
    ) {
        HomeContentCardCarousel(
            cards = dummyCards,
            modifier = Modifier,
            onCenterCardClick = {},
        )
    }
}