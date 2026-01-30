package com.kuit.archiveatproject.presentation.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.domain.entity.UserInterestGroup
import com.kuit.archiveatproject.domain.entity.UserMetadataCategory
import com.kuit.archiveatproject.domain.entity.UserMetadataTopic
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.onboarding.component.OnboardingNextButton
import com.kuit.archiveatproject.presentation.onboarding.component.interest.InterestCategorySection
import com.kuit.archiveatproject.presentation.onboarding.component.interest.InterestTextChip
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingUiEvent
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingUiState
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun OnboardingInterestScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    val uiState by viewModel.uiState.collectAsState()

    OnboardingInterestContent(
        uiState = uiState,
        onTopicToggled = { categoryId, topicId ->
            // 지금 contract 기준: UI에서 list 만들어서 전달
            val updated = toggleInterest(
                current = uiState.selectedInterests,
                categoryId = categoryId,
                topicId = topicId
            )
            viewModel.onEvent(
                OnboardingUiEvent.OnInterestsSelected(updated)
            )
        },
        onSubmitClicked = {
            viewModel.onEvent(OnboardingUiEvent.OnSubmit)
        }
    )
}

@Composable
private fun OnboardingInterestContent(
    uiState: OnboardingUiState,
    onTopicToggled: (categoryId: Long, topicId: Long) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val selectedTopicIds = remember(uiState.selectedInterests) {
        uiState.selectedInterests
            .flatMap { it.topicIds }
            .toSet()
    }

    val selectedCount = selectedTopicIds.size
    val isSubmitEnabled = selectedCount >= 5

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(Modifier.height(44.dp))

        // ===== Header =====
        Column(
            modifier = Modifier.padding(horizontal = 26.dp)
        ) {

        }

        Spacer(Modifier.height(16.dp))

        // ===== Scroll Area =====
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 26.dp)
        ) {
            item {
                Text(
                    text = "요즘 가장 집중하고 있는\n관심 주제를 골라주세요!",
                    style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray950
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "선택하신 주제는 ",
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray400
                    )
                    InterestTextChip(
                        text = "영감수집",
                    )
                    Spacer(Modifier.width(2.5.dp))
                    InterestTextChip(
                        text = "집중탐구",
                    )
                    Text(
                        text = " 로 확실히 챙겨드리고,",
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray400
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "나머지도 ",
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray400
                    )
                    InterestTextChip(
                        text = "성장한입",
                    )
                    Spacer(Modifier.width(2.5.dp))
                    InterestTextChip(
                        text = "관점확장",
                    )
                    Text(
                        text = " 으로 가능성을 열어드릴게요",
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray400
                    )
                }
                Spacer(Modifier.height(28.dp))
            }

            uiState.interestCategories.forEach { category ->
                item {
                    InterestCategorySection(
                        category = category,
                        selectedTopicIds = selectedTopicIds,
                        onTopicClicked = { topicId ->
                            onTopicToggled(category.id, topicId)
                        }
                    )
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        // ===== CTA =====
        OnboardingNextButton(
            text = if (isSubmitEnabled)
                "archiveat! 시작하기 (${selectedCount}개 선택)"
            else
                "archiveat! 시작하기",
            enabled = isSubmitEnabled,
            onClick = onSubmitClicked,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(34.dp))
    }
}

private fun toggleInterest(
    current: List<UserInterestGroup>,
    categoryId: Long,
    topicId: Long
): List<UserInterestGroup> {

    val group = current.find { it.categoryId == categoryId }

    return if (group == null) {
        // 아직 해당 카테고리가 없으면 새로 추가
        current + UserInterestGroup(
            categoryId = categoryId,
            topicIds = listOf(topicId)
        )
    } else {
        val newTopics =
            if (topicId in group.topicIds) {
                group.topicIds - topicId
            } else {
                group.topicIds + topicId
            }

        if (newTopics.isEmpty()) {
            // 토픽이 하나도 없으면 그룹 제거
            current - group
        } else {
            // 해당 카테고리만 갱신
            current.map {
                if (it.categoryId == categoryId)
                    it.copy(topicIds = newTopics)
                else it
            }
        }
    }
}

private val previewCategories = listOf(
    UserMetadataCategory(
        id = 1,
        name = "IT/과학",
        topics = listOf(
            UserMetadataTopic(1, "인공지능"),
            UserMetadataTopic(2, "백엔드/인프라"),
            UserMetadataTopic(3, "프론트엔드"),
            UserMetadataTopic(4, "데이터/보안"),
            UserMetadataTopic(5, "테크 트렌드"),
            UserMetadataTopic(6, "블록체인"),
        )
    ),
    UserMetadataCategory(
        id = 2,
        name = "경제",
        topics = listOf(
            UserMetadataTopic(7, "주식/투자"),
            UserMetadataTopic(8, "부동산"),
            UserMetadataTopic(9, "가상 화폐"),
            UserMetadataTopic(10, "창업/스타트업"),
            UserMetadataTopic(11, "브랜드/마케팅"),
            UserMetadataTopic(12, "거시경제"),
        )
    ),
    UserMetadataCategory(
        id = 3,
        name = "국제",
        topics = listOf(
            UserMetadataTopic(13, "지정학/외교"),
            UserMetadataTopic(14, "미국/중국"),
            UserMetadataTopic(15, "글로벌 비즈니스"),
            UserMetadataTopic(16, "기후/에너지"),
        )
    ),
    UserMetadataCategory(
        id = 4,
        name = "문화",
        topics = listOf(
            UserMetadataTopic(17, "영화/OTT"),
            UserMetadataTopic(18, "음악"),
            UserMetadataTopic(19, "도서/아트"),
            UserMetadataTopic(20, "팝컬처/트렌드"),
            UserMetadataTopic(21, "공간/플레이스"),
            UserMetadataTopic(22, "디자인/예술"),
        )
    ),
    UserMetadataCategory(
        id = 5,
        name = "생활",
        topics = listOf(
            UserMetadataTopic(23, "주니어/취업"),
            UserMetadataTopic(24, "업무 생산성"),
            UserMetadataTopic(25, "리더십/조직"),
            UserMetadataTopic(26, "심리/마인드"),
            UserMetadataTopic(27, "건강/리빙"),
        )
    )
)

@Preview(
    showBackground = true,
)
@Composable
fun OnboardingInterestContentPreview() {

    var uiState by remember {
        mutableStateOf(
            OnboardingUiState(
                interestCategories = previewCategories,
                selectedInterests = emptyList()
            )
        )
    }

    ArchiveatProjectTheme {
        OnboardingInterestContent(
            uiState = uiState,
            onTopicToggled = { categoryId, topicId ->
                uiState = uiState.copy(
                    selectedInterests = toggleInterest(
                        current = uiState.selectedInterests,
                        categoryId = categoryId,
                        topicId = topicId
                    )
                )
            },
            onSubmitClicked = {
                // Preview에서는 noop
            }
        )
    }
}
