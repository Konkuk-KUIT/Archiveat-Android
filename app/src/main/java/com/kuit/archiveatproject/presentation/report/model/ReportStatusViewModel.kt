package com.kuit.archiveatproject.presentation.report.model

import androidx.lifecycle.ViewModel
import com.kuit.archiveatproject.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportStatusViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel(){
    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState

}