package com.kuit.archiveatproject.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.domain.entity.UserAvailability
import com.kuit.archiveatproject.domain.entity.UserInterests
import com.kuit.archiveatproject.domain.entity.UserMetadataSubmit
import com.kuit.archiveatproject.domain.repository.UserMetadataRepository
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userMetadataRepository: UserMetadataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<OnboardingNavigationEvent>(
        extraBufferCapacity = 1
    )
    val navigationEvent: SharedFlow<OnboardingNavigationEvent> = _navigationEvent.asSharedFlow()

    private fun Set<TimeSlot>.toggle(timeSlot: TimeSlot): Set<TimeSlot> =
        if (contains(timeSlot)) this - timeSlot else this + timeSlot

    fun onEvent(event: OnboardingUiEvent) {
        when (event) {

            OnboardingUiEvent.OnEnter -> {
                loadMetadata()
            }

            is OnboardingUiEvent.OnEmploymentSelected -> {
                _uiState.update {
                    it.copy(
                        selectedEmploymentType = event.employment.type
                    )
                }
            }

            is OnboardingUiEvent.OnTimeSlotToggled -> {
                _uiState.update { state ->
                    when (event.mode) {
                        ReadingMode.LIGHT -> {
                            if (event.timeSlot in state.deepReadingTimes) {
                                state
                            } else {
                                state.copy(
                                    lightReadingTimes =
                                        state.lightReadingTimes.toggle(event.timeSlot)
                                )
                            }
                        }

                        ReadingMode.DEEP -> {
                            if (event.timeSlot in state.lightReadingTimes) {
                                state
                            } else {
                                state.copy(
                                    deepReadingTimes =
                                        state.deepReadingTimes.toggle(event.timeSlot)
                                )
                            }
                        }
                    }
                }
            }

            is OnboardingUiEvent.OnInterestsSelected -> {
                _uiState.update {
                    it.copy(
                        selectedInterests = event.interests
                    )
                }
            }

            OnboardingUiEvent.OnNextStep -> {
                moveToNextStep()
            }

            OnboardingUiEvent.OnSubmit -> {
                submitMetadata()
            }
        }
    }

    /**
     * GET /user/metadata
     */
    private fun loadMetadata() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                userMetadataRepository.getUserMetadata()
            }.onSuccess { result ->
                _uiState.update {
                    it.copy(
                        employmentOptions = mapEmploymentTypes(result.employmentTypes),
                        availabilityOptions = result.availabilityOptions.map {
                            TimeSlot.valueOf(it)
                        },
                        interestCategories = result.categories,
                        isLoading = false
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    /**
     * POST /user/metadata
     */
    private fun submitMetadata() {
        val state = _uiState.value

        // 필수 값 검증
        if (state.selectedEmploymentType == null ||
            (state.lightReadingTimes.isEmpty() && state.deepReadingTimes.isEmpty())
        ) {
            _uiState.update {
                it.copy(errorMessage = "필수 정보를 선택해주세요.")
            }
            return
        }

        val submitEntity = UserMetadataSubmit(
            employmentType = state.selectedEmploymentType,
            availability = UserAvailability(
                light = state.lightReadingTimes.toList(),
                deep = state.deepReadingTimes.toList()
            ),
            interests = state.selectedInterests
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = userMetadataRepository.submitUserMetadata(submitEntity)

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
                _navigationEvent.tryEmit(OnboardingNavigationEvent.SubmitSuccess)
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    /**
     * 다음 버튼을 눌렀을 때만 step 이동
     */
    private fun moveToNextStep() {
        val state = _uiState.value

        if (!state.isNextEnabled) return

        _uiState.update {
            it.copy(step = OnboardingStep.INTERESTS)
        }
    }

    private fun mapEmploymentTypes(
        types: List<String>
    ): List<JobUiModel> {
        return types.map { type ->
            when (type) {
                "STUDENT" -> JobUiModel(
                    type = type,
                    label = "대학생",
                    iconRes = R.drawable.ic_job_student
                )

                "EMPLOYEE" -> JobUiModel(
                    type = type,
                    label = "직장인",
                    iconRes = R.drawable.ic_job_employee
                )

                "FREELANCER" -> JobUiModel(
                    type = type,
                    label = "프리랜서",
                    iconRes = R.drawable.ic_job_freelancer
                )

                else -> JobUiModel(
                    type = type,
                    label = type,
                    iconRes = R.drawable.ic_job_etc
                )
            }
        }
    }

    fun toggleInterest(
        current: List<UserInterests>,
        categoryId: Long,
        topicId: Long
    ): List<UserInterests> {

        val group = current.find { it.categoryId == categoryId }

        return if (group == null) {
            current + UserInterests(
                categoryId = categoryId,
                topicIds = listOf(topicId)
            )
        } else {
            val newTopics =
                if (topicId in group.topicIds)
                    group.topicIds - topicId
                else
                    group.topicIds + topicId

            if (newTopics.isEmpty()) {
                current - group
            } else {
                current.map {
                    if (it.categoryId == categoryId)
                        it.copy(topicIds = newTopics)
                    else it
                }
            }
        }
    }

    fun onInterestToggled(
        categoryId: Long,
        topicId: Long
    ) {
        _uiState.update { state ->
            state.copy(
                selectedInterests = toggleInterest(
                    current = state.selectedInterests,
                    categoryId = categoryId,
                    topicId = topicId
                )
            )
        }
    }
}

val jobs = listOf(
    JobUiModel(
        type = "STUDENT",
        label = "학생",
        iconRes = R.drawable.ic_job_student
    ),
    JobUiModel(
        type = "EMPLOYEE",
        label = "직장인",
        iconRes = R.drawable.ic_job_employee
    ),
    JobUiModel(
        type = "FREELANCER",
        label = "프리랜서",
        iconRes = R.drawable.ic_job_freelancer
    ),
    JobUiModel(
        type = "ETC",
        label = "기타",
        iconRes = R.drawable.ic_job_etc
    ),
)
