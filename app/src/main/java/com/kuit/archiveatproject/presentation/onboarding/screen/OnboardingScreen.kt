package com.kuit.archiveatproject.presentation.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.onboarding.component.OnboardingActionButton
import com.kuit.archiveatproject.presentation.onboarding.component.OnboardingBottomWrapper
import com.kuit.archiveatproject.presentation.onboarding.component.OnboardingIndicator
import com.kuit.archiveatproject.presentation.onboarding.component.OnboardingSlideContent
import com.kuit.archiveatproject.presentation.onboarding.model.OnboardingButtonStyle
import com.kuit.archiveatproject.presentation.onboarding.model.OnboardingSlideModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import com.kuit.archiveatproject.ui.theme.Gray200

val onboardingSlides = listOf(
    OnboardingSlideModel(
        imageRes = R.drawable.img_onboarding_1,
        title = "링크, 스크린샷, 노트까지\n공유 한번으로 저장해요"
    ),
    OnboardingSlideModel(
        imageRes = R.drawable.img_onboarding_1,
        title = "관심사 기반으로\n콘텐츠를 추천해요"
    ),
    OnboardingSlideModel(
        imageRes = R.drawable.img_onboarding_1,
        title = "나만의 아카이브를\n완성해보세요"
    )
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableStateOf(0) }

    val totalPage = onboardingSlides.size
    val lastPage = onboardingSlides.lastIndex

    val prevButtonStyle = OnboardingButtonStyle(
        backgroundColor = ArchiveatProjectTheme.colors.gray200,
        textColor = ArchiveatProjectTheme.colors.gray600
    )

    val nextButtonStyle = OnboardingButtonStyle(
        backgroundColor = ArchiveatProjectTheme.colors.primary,
        textColor = ArchiveatProjectTheme.colors.white
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {

        // 🔹 상단 콘텐츠 영역
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 88.dp) // ⭐ 버튼 영역만큼 확보
        ) {

            Spacer(Modifier.height(95.dp))

            OnboardingIndicator(
                totalPage = totalPage,
                currentPage = currentPage
            )

            Spacer(Modifier.height(70.dp))

            OnboardingSlideContent(
                slide = onboardingSlides[currentPage],
                modifier = Modifier.fillMaxSize()
                // ❌ weight 제거
            )
        }

        // 🔹 하단 고정 버튼 래퍼
        OnboardingBottomWrapper(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {

            OnboardingActionButton(
                text = "이전",
                style = prevButtonStyle,
                enabled = currentPage > 0,
                modifier = Modifier.weight(1f),
                onClick = { currentPage-- }
            )

            OnboardingActionButton(
                text = if (currentPage == lastPage) "시작하기" else "다음",
                style = nextButtonStyle,
                modifier = Modifier.weight(1f),
                onClick = {
                    if (currentPage < lastPage) currentPage++
                    else {
                        // 온보딩 종료
                    }
                }
            )
        }
    }
}

@Preview(
    name = "OnboardingScreen",
    showBackground = true
)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        modifier = Modifier.fillMaxSize()
    )
}
