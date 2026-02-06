package com.kuit.archiveatproject.presentation.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

private val EMAIL_REGEX =
    Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$")

private fun isValidEmail(raw: String, maxLen: Int): Boolean {
    val email = raw.trim()
    return email.isNotEmpty() &&
            email.length <= maxLen &&
            EMAIL_REGEX.matches(email)
}

@Composable
fun LoginStep3(
    email: String,
    password: String,
    isLoading: Boolean,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onBack: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val passwordFocusRequester = remember { FocusRequester() }

    // 길이 제한
    val emailMaxLen = 254 // RFC 5321 표준

    val passwordMinLen = 8
    val passwordMaxLen = 20 // 128

    var isPasswordVisible by remember { mutableStateOf(false) }

    val emailTrimmed = email.trim()

    val isEmailValid = isValidEmail(email, emailMaxLen)
    val isPasswordValid = password.length in passwordMinLen..passwordMaxLen
    val isFormValid = isEmailValid && isPasswordValid

    val showPasswordError = password.isNotEmpty() && !isPasswordValid

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        BackTopBar(onBack = onBack, title = "", height = 56)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 22.dp)
        ) {
            Column(
                modifier = Modifier.padding(6.dp),
            )
            {
                Row {
                    Text(
                        text = "archiveat! ",
                        style = ArchiveatProjectTheme.typography.Logo_regular,
                        color = ArchiveatProjectTheme.colors.gray950
                    )
                    Text(
                        text = "에서 사용할",
                        style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                        color = ArchiveatProjectTheme.colors.gray950
                    )
                }
                Text(
                    text = "이메일과 비밀번호를 입력하세요.",
                    style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray950
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = "이메일",
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray950,
                modifier = Modifier.padding(6.dp),
            )

            Spacer(modifier = Modifier.height(14.dp))

            TextField(
                value = email,
                onValueChange = { input ->
                    onEmailChange(
                        input.take(emailMaxLen) // 254자 넘으면 자르기
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = !isLoading,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                textStyle = ArchiveatProjectTheme.typography.Subhead_2_semibold.copy(
                    color = ArchiveatProjectTheme.colors.gray800
                ),
                placeholder = {
                    Text(
                        text = "이메일을 입력해주세요",
                        style = ArchiveatProjectTheme.typography.Body_2_semibold, // Subhead_2_semibold
                        color = ArchiveatProjectTheme.colors.gray400
                    )
                },
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Text(
                            text = "${emailTrimmed.length}/$emailMaxLen",
                            style = ArchiveatProjectTheme.typography.Body_2_medium,
                            color = ArchiveatProjectTheme.colors.gray400
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ArchiveatProjectTheme.colors.gray100,
                    unfocusedContainerColor = ArchiveatProjectTheme.colors.gray100,
                    disabledContainerColor = ArchiveatProjectTheme.colors.gray100,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = ArchiveatProjectTheme.colors.gray950
                )
            )

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "비밀번호",
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray950,
                modifier = Modifier.padding(6.dp),
            )

            Spacer(modifier = Modifier.height(14.dp))

            TextField(
                value = password,
                onValueChange = { input ->
                    onPasswordChange(
                        input.take(passwordMaxLen)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .focusRequester(passwordFocusRequester),
                enabled = !isLoading,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                textStyle = ArchiveatProjectTheme.typography.Subhead_2_semibold.copy(
                    color = ArchiveatProjectTheme.colors.gray800
                ),
                placeholder = {
                    Text(
                        text = "비밀번호를 입력해주세요",
                        style = ArchiveatProjectTheme.typography.Body_2_semibold,
                        color = ArchiveatProjectTheme.colors.gray400
                    )
                },
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Text(
                            text = "${password.length}/$passwordMaxLen",
                            style = ArchiveatProjectTheme.typography.Body_2_medium,
                            color = ArchiveatProjectTheme.colors.gray400
                        )
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible)
                                    androidx.compose.material.icons.Icons.Filled.VisibilityOff
                                else
                                    androidx.compose.material.icons.Icons.Filled.Visibility,
                                contentDescription = if (isPasswordVisible) "비밀번호 숨기기" else "비밀번호 보기",
                                tint = ArchiveatProjectTheme.colors.gray400
                            )
                        }
                    }
                },
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard?.hide()
                        focusManager.clearFocus(force = true)
                        if (isFormValid) onComplete()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ArchiveatProjectTheme.colors.gray100,
                    unfocusedContainerColor = ArchiveatProjectTheme.colors.gray100,
                    disabledContainerColor = ArchiveatProjectTheme.colors.gray100,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = ArchiveatProjectTheme.colors.gray950
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "비밀번호는 8자 이상이어야 합니다.",
                style = ArchiveatProjectTheme.typography.Caption_medium,
                color = if (showPasswordError) {
                    ArchiveatProjectTheme.colors.sub_2
                } else {
                    ArchiveatProjectTheme.colors.gray400
                },
                modifier = Modifier.padding(horizontal = 13.dp),
            )

            // 에러 메시지: 테스트하고 나중에 지우거나... 처리하면 될 듯
            if (!errorMessage.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    style = ArchiveatProjectTheme.typography.Caption_medium,
                    color = ArchiveatProjectTheme.colors.sub_2,
                    modifier = Modifier.padding(horizontal = 13.dp),
                )
            }
        }

        PrimaryRoundedButton(
            text = "다음",
            onClick = {
                if (!isFormValid) return@PrimaryRoundedButton
                keyboard?.hide()
                focusManager.clearFocus(force = true) // 키보드 내림
                onComplete()
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp),
            cornerRadiusDp = 12,
            heightDp = 50,
            containerColor = ArchiveatProjectTheme.colors.deepPurple
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginStep3Preview() {
    LoginStep3(
        email = "test@archiveat.com",
        password = "password123",
        isLoading = false,
        errorMessage = "이메일/비밀번호 형식을 확인해주세요.",
        onEmailChange = {},
        onPasswordChange = {},
        onBack = {},
        onComplete = {}
    )
}
