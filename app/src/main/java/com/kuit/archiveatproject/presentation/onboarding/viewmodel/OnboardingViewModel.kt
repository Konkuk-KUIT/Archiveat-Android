package com.kuit.archiveatproject.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.domain.entity.UserInterests
import com.kuit.archiveatproject.domain.entity.UserMetadataSubmit
import com.kuit.archiveatproject.domain.repository.UserMetadataRepository
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userMetadataRepository: UserMetadataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun onEvent(event: OnboardingUiEvent) {
        when (event) {

            OnboardingUiEvent.OnEnter -> {
                loadMetadata()
            }

            is OnboardingUiEvent.OnEmploymentSelected -> {
                _uiState.update {
                    it.copy(
                        selectedEmployment = event.employment
                    )
                }
            }

            is OnboardingUiEvent.OnAvailabilityChanged -> {
                _uiState.update {
                    it.copy(
                        availability = event.availability
                    )
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
                        availabilityOptions = result.availabilityOptions,
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
        if (state.selectedEmployment == null || state.availability == null) {
            _uiState.update {
                it.copy(errorMessage = "필수 정보를 선택해주세요.")
            }
            return
        }

        val submitEntity = UserMetadataSubmit(
            employmentType = state.selectedEmployment.type,
            availability = state.availability,
            interests = UserInterests(
                now = state.selectedInterests,
                future = emptyList()
            )
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = userMetadataRepository.submitUserMetadata(submitEntity)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSubmitSuccess = true
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
                    iconRes = R.drawable.ic_job_student
                )

                "FREELANCER" -> JobUiModel(
                    type = type,
                    label = "프리랜서",
                    iconRes = R.drawable.ic_job_student
                )

                else -> JobUiModel(
                    type = type,
                    label = type,
                    iconRes = R.drawable.ic_job_student
                )
            }
        }
    }
}