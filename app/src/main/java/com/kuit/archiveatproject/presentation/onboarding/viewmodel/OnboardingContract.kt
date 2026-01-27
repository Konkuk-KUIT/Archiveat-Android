package com.kuit.archiveatproject.presentation.onboarding.viewmodel

import com.kuit.archiveatproject.domain.entity.UserAvailability
import com.kuit.archiveatproject.domain.entity.UserInterestGroup
import com.kuit.archiveatproject.domain.entity.UserMetadataCategory

/**
 * 이 온보딩 화면이 가질 수 있는 상태 정의
 */
data class OnboardingUiState(
    // step
    val step: OnboardingStep = OnboardingStep.BASIC_INFO,

    // 서버에서 내려준 메타데이터 (GET)
    val employmentOptions: List<String> = emptyList(),
    val availabilityOptions: List<String> = emptyList(),
    val interestCategories: List<UserMetadataCategory> = emptyList(),

    // 사용자가 선택한 값
    val selectedEmployment: String? = null,
    val availability: UserAvailability? = null,
    val selectedInterests: List<UserInterestGroup> = emptyList(),

    // UI 상태
    val isLoading: Boolean = false,
    val isSubmitSuccess: Boolean = false,
    val errorMessage: String? = null,
) {
    // 다음 버튼 활성화 조건
    val isNextEnabled: Boolean
        get() = selectedEmployment != null && availability != null
}


/**
 * 온보딩 단계
 */
enum class OnboardingStep {
    BASIC_INFO,   // 직업 + 시간대
    INTERESTS
}

/**
 * 화면에서 발생할 수 있는 모든 이벤트
 */
sealed interface OnboardingUiEvent {

    object OnEnter : OnboardingUiEvent

    data class OnEmploymentSelected(
        val employment: String
    ) : OnboardingUiEvent

    data class OnAvailabilityChanged(
        val availability: UserAvailability
    ) : OnboardingUiEvent

    object OnNextStep : OnboardingUiEvent

    data class OnInterestsSelected(
        val interests: List<UserInterestGroup>
    ) : OnboardingUiEvent

    object OnSubmit : OnboardingUiEvent
}
