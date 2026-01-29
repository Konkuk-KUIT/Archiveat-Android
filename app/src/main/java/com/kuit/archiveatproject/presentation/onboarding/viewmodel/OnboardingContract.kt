package com.kuit.archiveatproject.presentation.onboarding.viewmodel

import com.kuit.archiveatproject.domain.entity.UserAvailability
import com.kuit.archiveatproject.domain.entity.UserInterestGroup
import com.kuit.archiveatproject.domain.entity.UserMetadataCategory
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel

/**
 * 이 온보딩 화면이 가질 수 있는 상태 정의
 */
data class OnboardingUiState(
    // step
    val step: OnboardingStep = OnboardingStep.BASIC_INFO,

    // 서버에서 내려준 메타데이터 (GET)
    val employmentOptions: List<JobUiModel> = emptyList(),
    val availabilityOptions: List<TimeSlot> = emptyList(),
    val interestCategories: List<UserMetadataCategory> = emptyList(),

    // 사용자가 선택한 값
    val selectedEmploymentType: String? = null,
    val lightReadingTimes: Set<TimeSlot> = emptySet(),
    val deepReadingTimes: Set<TimeSlot> = emptySet(),
    val selectedInterests: List<UserInterestGroup> = emptyList(),

    // UI 상태
    val isLoading: Boolean = false,
    val isSubmitSuccess: Boolean = false,
    val errorMessage: String? = null,
) {
    // step2 노출 조건
    val isStep2Visible: Boolean
        get() = selectedEmploymentType != null
    // 다음 버튼 활성화 조건
    val isNextEnabled: Boolean
        get() = isStep2Visible &&
                (lightReadingTimes.isNotEmpty() || deepReadingTimes.isNotEmpty())
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
        val employment: JobUiModel
    ) : OnboardingUiEvent

    // Step 2
    data class OnTimeSlotToggled(
        val mode: ReadingMode,
        val timeSlot: TimeSlot
    ) : OnboardingUiEvent

    // Step 3
    data class OnInterestsSelected(
        val interests: List<UserInterestGroup>
    ) : OnboardingUiEvent

    // Navigation
    object OnNextStep : OnboardingUiEvent
    object OnSubmit : OnboardingUiEvent
}

enum class TimeSlot {
    MORNING,    // 등굣길(아침)
    LUNCHTIME,  // 공강/점심
    EVENING,    // 하굣길(저녁)
    BEDTIME     // 자기 전
}

enum class ReadingMode {
    LIGHT,  // 가볍게 읽기 (10분 미만)
    DEEP    // 몰입해 읽기 (10분 이상)
}