package com.kuit.archiveatproject.presentation.login.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.presentation.login.uistate.LoginStep
import com.kuit.archiveatproject.presentation.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onSignupSuccess: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSignupSuccess) {
        if (uiState.isSignupSuccess) {
            onSignupSuccess()
            viewModel.consumeSignupSuccess()
        }
    }

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            onLoginSuccess()
            viewModel.consumeLoginSuccess()
        }
    }

    when (uiState.step) {
        LoginStep.STEP1 -> {
            LoginStep1(
                onStartWithEmail = viewModel::onStartWithEmail,
                onStartLogin = viewModel::onStartLogin
            )
        }

        LoginStep.LOGIN -> {
            LoginStepLogin(
                email = uiState.email,
                password = uiState.password,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                onEmailChange = viewModel::onEmailChanged,
                onPasswordChange = viewModel::onPasswordChanged,
                onBack = viewModel::onBack,
                onLogin = viewModel::onLogin
            )
        }

        LoginStep.STEP2 -> {
            LoginStep2(
                agreementItems = uiState.agreementItems,
                isLoading = uiState.isLoading,
                onBack = viewModel::onBack,
                onToggleAll = viewModel::onAgreementAllToggled,
                onToggleItem = viewModel::onAgreementToggled,
                onNext = viewModel::onAgreementNext,
            )
        }

        LoginStep.STEP3 -> {
            LoginStep3(
                email = uiState.email,
                password = uiState.password,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                onEmailChange = viewModel::onEmailChanged,
                onPasswordChange = viewModel::onPasswordChanged,
                onBack = viewModel::onBack,
                onComplete = viewModel::onEmailPasswordNext
            )
        }

        LoginStep.STEP4 -> {
            LoginStep4(
                nickname = uiState.nickname,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                onNicknameChange = viewModel::onNicknameChanged,
                onBack = viewModel::onBack,
                onComplete = viewModel::onSignup
            )
        }
    }
}
