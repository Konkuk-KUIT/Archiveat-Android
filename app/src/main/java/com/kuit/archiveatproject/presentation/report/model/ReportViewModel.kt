package com.kuit.archiveatproject.presentation.report.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.kuit.archiveatproject.domain.entity.Report
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        fetchReport()
    }

    fun fetchReport() {
        viewModelScope.launch {
            runCatching { reportRepository.getReport() }
                .onSuccess { report ->
                    _uiState.value = report.toUiState()
                }
                .onFailure { throwable ->
                    Log.e("ReportViewModel", "fetchReport failed", throwable)
                    _uiState.value = ReportUiState(
                        isError = true,
                        errorMessage = throwable.message
                    )
                }
        }
    }

    private fun Report.toUiState(): ReportUiState =
        ReportUiState(
            referenceDate = weekLabel,
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
                InterestGapUiItem(
                    topicName = gap.topicName,
                    savedCount = gap.savedCount,
                    readCount = gap.readCount,
                    gap = (gap.savedCount - gap.readCount).coerceAtLeast(0)
                )
            },
            weeklyFeedbackWeekLabel = weekLabel,
            weeklyFeedbackBody = aiComment
        )
}
