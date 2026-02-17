package com.kuit.archiveatproject.presentation.report.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.repository.ReportRepository
import com.kuit.archiveatproject.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportBalanceViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState(isLoading = true))
    val uiState: StateFlow<ReportUiState> = _uiState

    fun fetchReportBalance() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, isError = false, errorMessage = null) }

        runCatching { userRepository.getNickname() }
            .onSuccess { nickname ->
                _uiState.update { it.copy(nickname = nickname) }
            }
            .onFailure { e ->
                Log.e("ReportBalanceVM", "getNickname failed", e)
            }

        runCatching {
            reportRepository.getReportBalance()
        }.onSuccess { balance ->
            _uiState.update { it.copy(balance = balance.toUiState(), isLoading = false) }
        }.onFailure { e ->
            Log.e("ReportBalanceVM", "fetchReportBalance failed", e)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "데이터를 불러오지 못했어요"
                )
            }
        }
    }

    enum class ReadingDepth { LIGHT, DEEP }
    enum class TimeFocus { NOW, FUTURE }

    fun ReportUiState.readingDepth(): ReadingDepth =
        if (balance.lightPercentage >= balance.deepPercentage) ReadingDepth.LIGHT else ReadingDepth.DEEP

    fun ReportUiState.timeFocus(): TimeFocus =
        if (balance.nowPercentage >= balance.futurePercentage) TimeFocus.NOW else TimeFocus.FUTURE
}
