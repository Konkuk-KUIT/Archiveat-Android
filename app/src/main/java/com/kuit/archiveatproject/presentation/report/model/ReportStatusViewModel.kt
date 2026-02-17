package com.kuit.archiveatproject.presentation.report.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.entity.ReportStatus
import com.kuit.archiveatproject.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ReportStatusViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState(isLoading = true))
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    fun fetchReportStatus() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isError = false, errorMessage = null)

            runCatching { reportRepository.getReportStatus() }
                .onSuccess { status ->
                    Log.d("ReportStatusVM", "getReportStatus success: saved=${status.totalSavedCount}, read=${status.totalReadCount}")

                    _uiState.value = status.toUiState()
                }
                .onFailure { e ->
                    if (e is CancellationException) throw e
                    Log.e("ReportStatusVM", "getReportStatus failed", e)
                    _uiState.value = ReportUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message ?: "네트워크 오류"
                    )
                }
        }
    }

    private fun ReportStatus.toUiState(): ReportUiState =
        ReportUiState(
            isLoading = false,
            totalSavedCount = totalSavedCount,
            totalReadCount = totalReadCount,
            readPercentage = percentage,
            recentReadNewsletters = recentReadNewsletters.map { n ->
                RecentReadNewsletterUiItem(
                    id = n.id,
                    title = n.title,
                    categoryName = n.categoryName,
                    lastViewedAt = n.lastViewedAt
                )
            }
        )
}
