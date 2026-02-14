package com.kuit.archiveatproject.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.core.util.ApiException
import com.kuit.archiveatproject.domain.repository.AuthRepository
import com.kuit.archiveatproject.presentation.login.uistate.LoginStep
import com.kuit.archiveatproject.presentation.login.uistate.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val emailRegex =
        Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$")

    private fun isValidEmail(raw: String, maxLen: Int): Boolean {
        val email = raw.trim()
        return email.isNotEmpty() && email.length <= maxLen && emailRegex.matches(email)
    }

    private fun Throwable.toUserMessage(defaultMessage: String): String {
        val statusCode = when (this) {
            is ApiException -> code
            is HttpException -> code()
            else -> null
        }
        val serverMessage = (message ?: "").trim()
        val hasServerMessage =
            serverMessage.isNotEmpty() &&
                !serverMessage.startsWith("HTTP ", ignoreCase = true) &&
                !serverMessage.equals("Unauthorized", ignoreCase = true)

        if (hasServerMessage) return serverMessage

        return when (statusCode) {
            400 -> "입력값을 다시 확인해주세요."
            401 -> "이메일 또는 비밀번호가 올바르지 않습니다."
            403 -> "접근 권한이 없습니다."
            404 -> "요청한 정보를 찾을 수 없습니다."
            409 -> "이미 가입된 이메일입니다."
            429 -> "요청이 많습니다. 잠시 후 다시 시도해주세요."
            in 500..599 -> "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
            else -> if (this is IOException) "네트워크 연결을 확인해주세요." else defaultMessage
        }
    }

    fun onStartWithEmail() {
        _uiState.update { it.copy(step = LoginStep.STEP2, errorMessage = null) }
    }

    fun onStartLogin() {
        _uiState.update { it.copy(step = LoginStep.LOGIN, errorMessage = null) }
    }

    fun onBack() {
        _uiState.update { state ->
            val prevStep = when (state.step) {
                LoginStep.STEP1 -> LoginStep.STEP1
                LoginStep.LOGIN -> LoginStep.STEP1
                LoginStep.STEP2 -> LoginStep.STEP1
                LoginStep.STEP3 -> LoginStep.STEP2
                LoginStep.STEP4 -> LoginStep.STEP3
            }
            state.copy(step = prevStep, errorMessage = null)
        }
    }

    fun onAgreementAllToggled() {
        _uiState.update { state ->
            val nextChecked = !state.allAgreementsChecked
            state.copy(
                agreementItems = state.agreementItems.map { it.copy(checked = nextChecked) },
                errorMessage = null
            )
        }
    }

    fun onAgreementToggled(id: String) {
        _uiState.update { state ->
            state.copy(
                agreementItems = state.agreementItems.map { item ->
                    if (item.id == id) item.copy(checked = !item.checked) else item
                },
                errorMessage = null
            )
        }
    }

    fun onAgreementNext() {
        val state = _uiState.value
        if (!state.requiredAgreementsChecked) {
            _uiState.update { it.copy(errorMessage = "필수 약관에 동의해주세요.") }
            return
        }
        _uiState.update { it.copy(step = LoginStep.STEP3, errorMessage = null) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onEmailPasswordNext() {
        val state = _uiState.value
        if (state.isLoading) return

        val email = state.email.trim()
        val isEmailValid = isValidEmail(email, 254)
        val isPasswordValid = state.password.length in 8..20

        if (!isEmailValid || !isPasswordValid) {
            _uiState.update { it.copy(errorMessage = "이메일/비밀번호 형식을 확인해주세요.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                authRepository.checkEmail(email)
            }.onSuccess { isAvailable ->
                if (isAvailable) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            step = LoginStep.STEP4,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "이미 가입된 이메일입니다."
                        )
                    }
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.toUserMessage("이메일 중복 확인에 실패했습니다.")
                    )
                }
            }
        }
    }

    fun onNicknameChanged(nickname: String) {
        _uiState.update { it.copy(nickname = nickname, errorMessage = null) }
    }

    fun onSignup() {
        val state = _uiState.value
        if (state.isLoading) return
        val nickname = state.nickname.trim()
        if (state.email.isBlank() || state.password.isBlank() || nickname.isBlank()) {
            _uiState.update { it.copy(errorMessage = "필수 정보를 입력해주세요.") }
            return
        }
        if (nickname.length !in 2..15) {
            _uiState.update { it.copy(errorMessage = "닉네임은 2~15자로 입력해주세요.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                authRepository.signup(
                    email = state.email.trim(),
                    password = state.password,
                    nickname = nickname
                )
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSignupSuccess = true) }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.toUserMessage("회원가입에 실패했습니다.")
                    )
                }
            }
        }
    }

    fun onLogin() {
        val state = _uiState.value
        if (state.isLoading) return

        val email = state.email.trim()
        val isEmailValid = isValidEmail(email, 254)
        val isPasswordValid = state.password.length in 8..20

        if (!isEmailValid || !isPasswordValid) {
            _uiState.update { it.copy(errorMessage = "이메일/비밀번호 형식을 확인해주세요.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                authRepository.login(
                    email = email,
                    password = state.password
                )
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false, isLoginSuccess = true) }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.toUserMessage("로그인에 실패했습니다.")
                    )
                }
            }
        }
    }

    fun consumeSignupSuccess() {
        _uiState.update { it.copy(isSignupSuccess = false) }
    }

    fun consumeLoginSuccess() {
        _uiState.update { it.copy(isLoginSuccess = false) }
    }
}
