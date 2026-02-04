package com.kuit.archiveatproject.presentation.inbox.edit

import com.kuit.archiveatproject.domain.entity.ExploreInboxEdit
import com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic
import com.kuit.archiveatproject.domain.entity.InboxCategory

// 드롭다운 어떤 메뉴가 열려있는지
enum class OpenMenu { NONE, CATEGORY, TOPIC }

data class InboxEditUiState(
    // 네트워크 에러 상태
    val isLoading: Boolean = false, // GET(/explore/inbox/{id}) 불러 오는 중인지
    val errorMessage: String? = null,

    // 수정 대상(인박스 아이템)
    val userNewsletterId: Long = -1L,

    // 서버에서 내려준 선택지 목록
    val categories: List<InboxCategory> = emptyList(),
    val topics: List<ExploreInboxEditTopic> = emptyList(),

    // 현재 사용자가 선택한 값
    val selectedCategoryId: Long? = null,
    val selectedTopicId: Long? = null,
    val memo: String = "",

    // 드롭다운 열림 상태
    val openMenu: OpenMenu = OpenMenu.NONE,

    // 저장 버튼/네트워크 처리용
    val isSaving: Boolean = false, // PATCH(/classification) 요청 중인지
    val savedOnce: Boolean = false,
) {
    // 현재 선택된 categoryId로 categories에서 name을 찾아서 반환 - 없으면 "" 반환
    fun selectedCategoryName(): String =
        categories.firstOrNull { it.id == selectedCategoryId }?.name.orEmpty()

    // 현재 선택된 topicId로 topics에서 name을 찾아서 반환 - 없으면 "" 반환
    fun selectedTopicName(): String =
        topics.firstOrNull { it.id == selectedTopicId }?.name.orEmpty()

    // 선택된 카테고리에 속하는 토픽들만 필터링해서 반환 - 카테고리가 선택되어 있지 않으면 빈 리스트
    fun filteredTopics(): List<ExploreInboxEditTopic> =
        selectedCategoryId?.let { cid -> topics.filter { it.categoryId == cid } } ?: emptyList()
}

/** 서버 GET 응답(ExploreInboxEdit) -> UiState 초기값 만들 때 사용 */
fun InboxEditUiState.applyServerData(edit: ExploreInboxEdit): InboxEditUiState {
    val categories = edit.categories
    val topics = edit.topics

    // categoryId가 null이면 "첫 번째 카테고리"를 fallback으로 선택
    val fallbackCategoryId = categories.firstOrNull()?.id
    val resolvedCategoryId =
        edit.current.categoryId
            ?.takeIf { cid -> categories.any { it.id == cid } }
            ?: fallbackCategoryId

    // resolvedCategoryId(결정된 카테고리)에 속한 토픽만 모음
    val topicsOfCategory =
        resolvedCategoryId?.let { cid -> topics.filter { it.categoryId == cid } }.orEmpty()
    // topicId가 null이면 해당 카테고리에 속한 첫 번째 토픽을 fallback으로 선택
    val fallbackTopicId = topicsOfCategory.firstOrNull()?.id
    val resolvedTopicId =
        edit.current.topicId
            ?.takeIf { tid -> topicsOfCategory.any { it.id == tid } }
            ?: fallbackTopicId

    // UiState에 반영 + 로딩/에러 상태 정리
    return copy(
        userNewsletterId = edit.current.userNewsletterId,
        categories = categories,
        topics = topics,
        selectedCategoryId = resolvedCategoryId,
        selectedTopicId = resolvedTopicId,
        memo = edit.current.memo.orEmpty(),
        isLoading = false,
        errorMessage = null
    )
}
