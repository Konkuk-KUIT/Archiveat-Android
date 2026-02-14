package com.kuit.archiveatproject.presentation.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.ApiException
import com.kuit.archiveatproject.domain.entity.UserAvailability
import com.kuit.archiveatproject.domain.entity.UserInterests
import com.kuit.archiveatproject.domain.entity.UserMetadataCategory
import com.kuit.archiveatproject.domain.entity.UserMetadataSubmit
import com.kuit.archiveatproject.domain.entity.UserMetadataTopic
import com.kuit.archiveatproject.domain.repository.AuthRepository
import com.kuit.archiveatproject.domain.repository.PendingSignupRepository
import com.kuit.archiveatproject.domain.repository.UserMetadataRepository
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import org.json.JSONObject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val pendingSignupRepository: PendingSignupRepository,
    private val userMetadataRepository: UserMetadataRepository,
) : ViewModel() {
    companion object {
        private const val TAG = "OnboardingViewModel"
    }

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<OnboardingNavigationEvent>(
        extraBufferCapacity = 1
    )
    val navigationEvent: SharedFlow<OnboardingNavigationEvent> = _navigationEvent.asSharedFlow()
    private var isSignupCompleted = false

    private fun Throwable.toUserMessage(defaultMessage: String): String {
        val statusCode = when (this) {
            is ApiException -> code
            is HttpException -> code()
            else -> null
        }
        val serverMessage = when (this) {
            is HttpException -> extractHttpErrorMessage()
            else -> (message ?: "").trim()
        }
        val hasServerMessage =
            serverMessage.isNotEmpty() &&
                !serverMessage.startsWith("HTTP ", ignoreCase = true) &&
                !serverMessage.equals("Unauthorized", ignoreCase = true) &&
                !serverMessage.equals("Forbidden", ignoreCase = true) &&
                !serverMessage.equals("Bad Request", ignoreCase = true) &&
                !serverMessage.equals("Not Found", ignoreCase = true) &&
                !serverMessage.equals("Conflict", ignoreCase = true) &&
                !serverMessage.equals("Too Many Requests", ignoreCase = true) &&
                !serverMessage.equals("Internal Server Error", ignoreCase = true)

        if (hasServerMessage) return serverMessage

        return when (statusCode) {
            400 -> "입력값을 다시 확인해주세요."
            401 -> "인증이 필요합니다. 다시 가입을 진행해주세요."
            403 -> "접근 권한이 없습니다."
            404 -> "요청한 정보를 찾을 수 없습니다."
            409 -> "이미 가입된 이메일입니다."
            429 -> "요청이 많습니다. 잠시 후 다시 시도해주세요."
            in 500..599 -> "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
            else -> if (this is IOException) "네트워크 연결을 확인해주세요." else defaultMessage
        }
    }

    private fun HttpException.extractHttpErrorMessage(): String {
        val rawBody = try {
            response()?.errorBody()?.string().orEmpty().trim()
        } catch (_: Exception) {
            ""
        }

        if (rawBody.isEmpty()) {
            return (message() ?: "").trim()
        }

        val parsedMessage = try {
            val json = JSONObject(rawBody)
            listOf("message", "error", "detail", "title")
                .firstNotNullOfOrNull { key ->
                    json.optString(key).takeIf { it.isNotBlank() }
                }
        } catch (_: Exception) {
            null
        }

        return (parsedMessage ?: rawBody).trim()
    }

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
                        availabilityOptions = result.availabilityOptions.mapNotNull { name ->
                            runCatching { TimeSlot.valueOf(name) }.getOrNull()
                        }.ifEmpty { TimeSlot.entries },
                        interestCategories = result.categories,
                        isLoading = false,
                        errorMessage = null,
                        isUsingFallbackData = false
                    )
                }
            }.onFailure { e ->
                Log.w(TAG, "Failed to load metadata. Using fallback defaults.", e)
                _uiState.update {
                    it.copy(
                        employmentOptions = mapEmploymentTypes(DEFAULT_EMPLOYMENT_TYPES),
                        availabilityOptions = TimeSlot.entries,
                        interestCategories = FALLBACK_CATEGORIES,
                        isLoading = false,
                        errorMessage = "메타데이터 로드 실패: 서버 데이터 대신 기본값을 사용합니다.",
                        isUsingFallbackData = true
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

        val draft = pendingSignupRepository.get()
        if (!isSignupCompleted && draft == null) {
            _uiState.update {
                it.copy(errorMessage = "가입 정보가 없습니다. 처음부터 다시 진행해주세요.")
            }
            _navigationEvent.tryEmit(OnboardingNavigationEvent.NavigateToSignupStart)
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                if (!isSignupCompleted) {
                    val signupDraft = requireNotNull(draft)
                    try {
                        authRepository.signup(
                            email = signupDraft.email,
                            password = signupDraft.password,
                            nickname = signupDraft.nickname
                        )
                        isSignupCompleted = true
                    } catch (e: ApiException) {
                        if (e.code == 409) {
                            isSignupCompleted = true
                        } else {
                            throw e
                        }
                    } catch (e: HttpException) {
                        if (e.code() == 409) {
                            isSignupCompleted = true
                        } else {
                            throw e
                        }
                    }
                }
                userMetadataRepository.submitUserMetadata(submitEntity).getOrThrow()
            }.onSuccess {
                pendingSignupRepository.clear()
                _uiState.update { it.copy(isLoading = false, errorMessage = null) }
                _navigationEvent.tryEmit(OnboardingNavigationEvent.SubmitSuccess)
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.toUserMessage("온보딩 완료에 실패했습니다. 다시 시도해주세요.")
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

private val DEFAULT_EMPLOYMENT_TYPES = listOf(
    "STUDENT",
    "EMPLOYEE",
    "FREELANCER",
    "ETC",
)

private val FALLBACK_CATEGORIES = listOf(
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
