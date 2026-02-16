package com.kuit.archiveatproject.presentation.report.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.entity.ReportInterestGap
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import com.kuit.archiveatproject.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportInterestGapAnalysisViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val exploreRepository: ExploreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportInterestGapAnalysisUiState(isLoading = true))
    val uiState: StateFlow<ReportInterestGapAnalysisUiState> = _uiState.asStateFlow()

    init {
        fetchInterestGap()
    }

    fun fetchInterestGap() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isError = false, errorMessage = null)
            runCatching { reportRepository.getReportInterestGap() }
                .onSuccess { topics ->
                    val exploreCategories = runCatching {
                        exploreRepository.getExplore().categories
                    }.getOrDefault(emptyList())
                    val topicIdByName = exploreCategories
                        .flatMap { it.topics }
                        .groupBy { it.name }
                        .mapValues { (_, groupedTopics) ->
                            // 이름이 유일한 경우만 매핑한다. (예: "기타"는 카테고리마다 중복)
                            groupedTopics.singleOrNull()?.id
                        }
                    val topicMetaById = exploreCategories
                        .flatMap { category ->
                            category.topics.map { topic ->
                                topic.id to TopicMeta(
                                    categoryName = category.name,
                                    topicName = topic.name
                                )
                            }
                        }
                        .toMap()

                    _uiState.value = ReportInterestGapAnalysisUiState(
                        topics = topics.map { it.toUiModel(topicIdByName, topicMetaById) },
                        isLoading = false
                    )
                }
                .onFailure { throwable ->
                    Log.e("ReportInterestGapVM", "fetchInterestGap failed", throwable)
                    _uiState.value = ReportInterestGapAnalysisUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = throwable.message
                    )
                }
        }
    }

    private fun ReportInterestGap.toUiModel(
        topicIdByName: Map<String, Long?>,
        topicMetaById: Map<Long, TopicMeta>
    ): InterestGapTopicUiModel {
        val resolvedTopicId = id ?: topicIdByName[topicName]
        val bubbleTitle = if (topicName == "기타") {
            resolvedTopicId
                ?.let { topicMetaById[it] }
                ?.let { "${it.categoryName}-${it.topicName}" }
                ?: topicName
        } else {
            topicName
        }
        return InterestGapTopicUiModel(
            id = resolvedTopicId ?: topicName.hashCode().toLong(),
            name = topicName,
            savedCount = savedCount,
            readCount = readCount,
            topicId = resolvedTopicId,
            bubbleTitle = bubbleTitle
        )
    }

    private data class TopicMeta(
        val categoryName: String,
        val topicName: String
    )
}
