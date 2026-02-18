package com.kuit.archiveatproject.presentation.report.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import com.kuit.archiveatproject.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val exploreRepository: ExploreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    fun fetchReport() {
        viewModelScope.launch {

            val exploreCategories = runCatching {
                exploreRepository.getExplore().categories
            }.getOrDefault(emptyList())

            val topicMetaById = exploreCategories
                .flatMap { category ->
                    category.topics.map { topic ->
                        topic.id to category.name
                    }
                }
                .toMap()

            runCatching { reportRepository.getReport() }
                .onSuccess { report ->

                    _uiState.value = report.toUiState(topicMetaById)

                }
                .onFailure { throwable ->
                    _uiState.value = ReportUiState(
                        isError = true,
                        errorMessage = throwable.message
                    )
                }
        }
    }

    private fun Report.toUiState(
        topicMetaById: Map<Long, String>
    ): ReportUiState =
        ReportUiState(
            referenceDate = serverTimestamp,
            totalSavedCount = status.totalSavedCount,
            totalReadCount = status.totalReadCount,
            readPercentage = status.percentage,
            balance = ReportBalanceUiState(
                lightPercentage = balance.lightPercentage,
                deepPercentage = balance.deepPercentage,
                nowPercentage = balance.nowPercentage,
                futurePercentage = balance.futurePercentage,
                patternTitle = balance.patternTitle,
                patternDescription = balance.patternDescription,
                patternQuote = balance.patternQuote
            ),
            interestGaps = interestGaps.map { gap ->

                val categoryName = topicMetaById[gap.topicId]

                val resolvedTopicName =
                    if (gap.topicName == "기타" && categoryName != null)
                        "$categoryName-기타"
                    else
                        gap.topicName

                MainInterestGapUiItem(
                    topicName = resolvedTopicName,
                    savedCount = gap.savedCount,
                    readCount = gap.readCount
                )
            },
            weeklyFeedbackWeekLabel = weekLabel,
            weeklyFeedbackBody = aiComment
        )
}
