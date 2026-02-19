package com.kuit.archiveatproject.presentation.etc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.repository.AuthRepository
import com.kuit.archiveatproject.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EtcViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EtcUiState())
    val uiState: StateFlow<EtcUiState> = _uiState

    init {
        loadNickname()
    }

    private fun loadNickname() {
        viewModelScope.launch {
            runCatching { userRepository.getNickname() }
                .onSuccess { nickname ->
                    _uiState.update { it.copy(nickName = nickname) }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            runCatching { authRepository.logout() }
                .onSuccess {
                    _uiState.update { it.copy(isLogoutSuccess = true) }
                }
        }
    }

    fun consumeLogoutSuccess() {
        _uiState.update { it.copy(isLogoutSuccess = false) }
    }
}
