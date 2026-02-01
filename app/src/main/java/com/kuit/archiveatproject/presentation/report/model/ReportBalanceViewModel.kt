package com.kuit.archiveatproject.presentation.report.model

import androidx.lifecycle.ViewModel
import com.kuit.archiveatproject.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportBalanceViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel(){
    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState

    enum class ReadingDepth { LIGHT, DEEP }
    enum class TimeFocus { NOW, FUTURE }

    // 리딩 깊이 판단 (Light vs Deep)
    fun ReportUiState.readingDepth(): ReadingDepth =
        if (balance.lightPercentage >= balance.deepPercentage)
            ReadingDepth.LIGHT
        else
            ReadingDepth.DEEP

    // 시간 집중도 판단 (Now vs Future)
    fun ReportUiState.timeFocus(): TimeFocus =
        if (balance.nowPercentage >= balance.futurePercentage)
            TimeFocus.NOW
        else
            TimeFocus.FUTURE
}