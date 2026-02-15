package com.kuit.archiveatproject.presentation.report.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportBalanceViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState

    fun fetchReportBalance() = viewModelScope.launch {
        runCatching {
            reportRepository.getReportBalance() // Domain: ReportBalance
        }.onSuccess { balance ->
            _uiState.update { it.copy(balance = balance.toUiState()) } // Presentation으로 변환
        }.onFailure { e ->
            android.util.Log.e("ReportBalanceVM", "fetchReportBalance failed", e)
        }
    }

    enum class ReadingDepth { LIGHT, DEEP }
    enum class TimeFocus { NOW, FUTURE }

    fun ReportUiState.readingDepth(): ReadingDepth =
        if (balance.lightPercentage >= balance.deepPercentage) ReadingDepth.LIGHT else ReadingDepth.DEEP

    fun ReportUiState.timeFocus(): TimeFocus =
        if (balance.nowPercentage >= balance.futurePercentage) TimeFocus.NOW else TimeFocus.FUTURE
}
