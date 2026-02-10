package com.kuit.archiveatproject.presentation.login.uistate

data class LoginAgreementItem(
    val id: String,
    val title: String,
    val required: Boolean,
    val checked: Boolean,
)

enum class LoginStep {
    STEP1,
    LOGIN,
    STEP2,
    STEP3,
    STEP4,
}

data class LoginUiState(
    val step: LoginStep = LoginStep.STEP1,
    val agreementItems: List<LoginAgreementItem> = defaultAgreementItems(),
    val email: String = "",
    val password: String = "",
    val nickname: String = "",
    val isLoading: Boolean = false,
    val isSignupSuccess: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val errorMessage: String? = null,
) {
    val allAgreementsChecked: Boolean
        get() = agreementItems.all { it.checked }

    val requiredAgreementsChecked: Boolean
        get() = agreementItems.filter { it.required }.all { it.checked }
}

private fun defaultAgreementItems(): List<LoginAgreementItem> {
    return listOf(
        LoginAgreementItem("age14", "만 14세 이상입니다.", required = true, checked = false),
        LoginAgreementItem("terms", "서비스 이용약관 동의", required = true, checked = false),
        LoginAgreementItem("privacy", "개인정보 수집 및 이용 동의", required = true, checked = false),
        LoginAgreementItem("marketingUse", "마케팅 활용 동의", required = false, checked = false),
        LoginAgreementItem("marketingReceive", "마케팅 정보 수신 동의", required = false, checked = false),
    )
}
