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

                            // 이미 deep에 있으면 무시
                            if (event.timeSlot in state.deepReadingTimes) return@update state

                            val isSelected = event.timeSlot in state.lightReadingTimes
                            val current = state.lightReadingTimes

                            // 이미 2개 선택된 상태에서 새로 선택하려 하면 막기
                            if (!isSelected && current.size >= 2) {
                                return@update state
                            }

                            state.copy(
                                lightReadingTimes = current.toggle(event.timeSlot)
                            )
                        }

                        ReadingMode.DEEP -> {

                            if (event.timeSlot in state.lightReadingTimes) return@update state

                            val isSelected = event.timeSlot in state.deepReadingTimes
                            val current = state.deepReadingTimes

                            if (!isSelected && current.size >= 2) {
                                return@update state
                            }

                            state.copy(
                                deepReadingTimes = current.toggle(event.timeSlot)
                            )
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
    private fun normalizeAvailability(
        light: Set<TimeSlot>,
        deep: Set<TimeSlot>,
        allSlots: List<TimeSlot>
    ): Pair<Set<TimeSlot>, Set<TimeSlot>> {

        val totalSlots = allSlots.toList()

        val lightCount = light.size
        val deepCount = deep.size
        val totalSelected = lightCount + deepCount

        // 이미 4개고 3:1 or 2:2 or 1:3이면 그대로 유지
        if (totalSelected == 4) {
            return light to deep
        }

        // 3 / 0 → 3 / 1
        if (lightCount == 3 && deepCount == 0) {
            val remaining = totalSlots.first { it !in light }
            return light to setOf(remaining)
        }

        if (deepCount == 3 && lightCount == 0) {
            val remaining = totalSlots.first { it !in deep }
            return setOf(remaining) to deep
        }

        // 그 외는 무조건 2:2로 맞춘다
        val finalLight = totalSlots.take(2).toSet()
        val finalDeep = totalSlots.drop(2).toSet()

        return finalLight to finalDeep
    }

    private fun submitMetadata() {
        val state = _uiState.value

        // employment null 방지
        val employment = state.selectedEmploymentType
        if (employment == null) {
            _uiState.update {
                it.copy(errorMessage = "직업을 선택해주세요.")
            }
            return
        }

        // 시간 정규화 (항상 실행)
        val (normalizedLight, normalizedDeep) =
            normalizeAvailability(
                light = state.lightReadingTimes,
                deep = state.deepReadingTimes,
                allSlots = state.availabilityOptions
            )

        // 2:2 보장 체크
        if (normalizedLight.size != 2 || normalizedDeep.size != 2) {
            _uiState.update {
                it.copy(errorMessage = "시간 선택이 올바르지 않습니다.")
            }
            return
        }

        val submitEntity = UserMetadataSubmit(
            employmentType = employment,
            availability = UserAvailability(
                light = normalizedLight.toList(),
                deep = normalizedDeep.toList()
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

        val (normalizedLight, normalizedDeep) =
            normalizeAvailability(
                light = state.lightReadingTimes,
                deep = state.deepReadingTimes,
                allSlots = state.availabilityOptions
            )

        _uiState.update {
            it.copy(
                lightReadingTimes = normalizedLight,
                deepReadingTimes = normalizedDeep,
                step = OnboardingStep.INTERESTS
            )
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
                    label = "기타",
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
