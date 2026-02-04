package com.kuit.archiveatproject.presentation.inbox.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.repository.InboxClassificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InboxEditViewModel @Inject constructor(
    private val repo: InboxClassificationRepository,
    // Navigation Compose를 쓰면 "route argument"가 여기에 들어옴
    // - 프로세스 재시작/회전 같은 상황에도 값이 복원될 수 있음
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // NavArg로 "userNewsletterId"를 받는다고 가정
    // => 네비게이션에서 라우트 "inboxEdit/{userNewsletterId}" 가정
    private val argUserNewsletterId: Long =
        savedStateHandle.get<Long>("userNewsletterId") ?: -1L

    // 화면 상태 StateFlow
    private val _uiState = MutableStateFlow(
        InboxEditUiState(
            isLoading = true,
            userNewsletterId = argUserNewsletterId
        )
    )
    val uiState: StateFlow<InboxEditUiState> = _uiState

    // ViewModel 생성 시점에:
    // NavArg가 정상적으로 들어왔다면 fetch 호출해서 서버 데이터 로드 / 아니면 에러 처리
    init {
        if (argUserNewsletterId != -1L) fetch(argUserNewsletterId)
        else _uiState.update { it.copy(isLoading = false, errorMessage = "userNewsletterId가 없습니다") }
    }

    /** 수정 화면 조회 API 호출: GET /explore/inbox/{userNewsletterId} */
    fun fetch(userNewsletterId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, savedOnce = false) }
            runCatching { repo.getExploreInboxEdit(userNewsletterId) }
                .onSuccess { edit ->
                    _uiState.update { state -> state.applyServerData(edit) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "수정 화면 조회 실패"
                        )
                    }
                }
        }
    }

    // 카테고리 드롭다운 토글
    fun openCategoryMenu() {
        _uiState.update { it.copy(openMenu = if (it.openMenu == OpenMenu.CATEGORY) OpenMenu.NONE else OpenMenu.CATEGORY) }
    }

    // 토픽 드롭다운 토글
    fun openTopicMenu() {
        _uiState.update { it.copy(openMenu = if (it.openMenu == OpenMenu.TOPIC) OpenMenu.NONE else OpenMenu.TOPIC) }
    }

    // 바깥 클릭/메모 클릭 -> 메뉴 닫기
    fun dismissMenu() {
        _uiState.update { it.copy(openMenu = OpenMenu.NONE) }
    }

    // 메모 값 변경 + 드롭다운 닫기
    fun onMemoChange(newValue: String) {
        _uiState.update { it.copy(memo = newValue, openMenu = OpenMenu.NONE) } // ✅ 메모 입력 시 드롭다운 닫힘
    }

    // 카테고시 선택 시:
    // * selectedCategoryId 갱신
    // * "해당 카테고리에 속한 토픽 목록"에서 첫 토픽으로 selectedTopicId도 갱신
    // * 드롭다운 닫기
    fun selectCategory(categoryId: Long) {
        _uiState.update { state ->
            val newTopics = state.topics.filter { it.categoryId == categoryId }
            val newTopicId = newTopics.firstOrNull()?.id
            state.copy(
                selectedCategoryId = categoryId,
                selectedTopicId = newTopicId,
                openMenu = OpenMenu.NONE
            )
        }
    }

    // 토픽 선택 시: 선택값 갱신 + 닫기
    fun selectTopic(topicId: Long) {
        _uiState.update { it.copy(selectedTopicId = topicId, openMenu = OpenMenu.NONE) }
    }

    /** 저장: PATCH /explore/inbox/{userNewsletterId}/classification */
    fun save(onSaved: () -> Unit) {
        val state = _uiState.value
        val userNewsletterId = state.userNewsletterId
        val categoryId = state.selectedCategoryId
        val topicId = state.selectedTopicId

        if (userNewsletterId == -1L || categoryId == null || topicId == null) {
            _uiState.update { it.copy(errorMessage = "카테고리/토픽 선택값이 없습니다") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            runCatching {
                repo.patchInboxClassification(
                    userNewsletterId = userNewsletterId,
                    categoryId = categoryId,
                    topicId = topicId,
                    memo = state.memo
                )
            }
                .onSuccess {
                    _uiState.update { it.copy(isSaving = false, savedOnce = true) }
                    onSaved()
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = e.message ?: "저장 실패"
                        )
                    }
                }
        }
    }
}
