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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun LoginStep4(
    onBack: () -> Unit,
    onComplete: (nickname: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    val minLen = 2
    val maxLen = 15

    var nickname by remember { mutableStateOf("") }

    val nicknameText = nickname
    // 길이 조건
    val isValid = nicknameText.length in minLen..maxLen

    val keyboard = LocalSoftwareKeyboardController.current

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
                    text = "닉네임을 정해주세요",
                    style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray950
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = "닉네임",
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray950,
                modifier = Modifier.padding(6.dp),
            )

            Spacer(modifier = Modifier.height(14.dp))

            TextField(
                value = nickname,
                onValueChange = { input ->
                    nickname = input
                        .filter(::isNicknameCharAllowed) // 허용되지 않는 문자는 자동 제거
                        .take(maxLen) // 15자 넘으면 자르기
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                textStyle = ArchiveatProjectTheme.typography.Subhead_2_semibold.copy(
                    color = ArchiveatProjectTheme.colors.gray800
                ),
                placeholder = {
                    Text(
                        text = "닉네임을 입력해주세요",
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
                            text = "${nicknameText.length}/$maxLen",
                            style = ArchiveatProjectTheme.typography.Body_2_medium,
                            color = ArchiveatProjectTheme.colors.gray400
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard?.hide()
                        focusManager.clearFocus(force = true)
                        if (isValid) onComplete(nicknameText) // 엔터 누르면 isValid일 떄 onComplete(...
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
                text = "한글, 영문 대소문자, 숫자, 특수문자 밑줄(_), 마침표(.)를 포함하여 2~15자를 지원합니다.",
                style = ArchiveatProjectTheme.typography.Caption_medium,
                color = ArchiveatProjectTheme.colors.gray400,
                modifier = Modifier.padding(horizontal = 13.dp),
            )
        }

        PrimaryRoundedButton(
            text = "다음",
            onClick = {
                if (!isValid) return@PrimaryRoundedButton
                keyboard?.hide()
                focusManager.clearFocus(force = true) // 키보드 내림
                onComplete(nicknameText) // (nickname: String) -> Unit (부모에게 닉네임 전달)
            },
            enabled = isValid,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp),
            cornerRadiusDp = 12,
            heightDp = 50,
            containerColor = ArchiveatProjectTheme.colors.deepPurple
        )
    }
}

/**
 * 허용: 한글, 영문(대/소), 숫자, '_' '.'
 */
private fun isNicknameCharAllowed(ch: Char): Boolean {
    return (ch in 'a'..'z') ||
            (ch in 'A'..'Z') ||
            (ch in '0'..'9') ||
            ch == '_' || ch == '.' || isKorean(ch)
}

private fun isKorean(ch: Char): Boolean {
    val code = ch.code
    return (code in 0xAC00..0xD7A3) || (code in 0x3131..0x318E) // 가-힣, ㄱ-ㅎ/ㅏ-ㅣ 범위 허용
}

@Preview(showBackground = true)
@Composable
private fun LoginStep4Preview() {
    LoginStep4(onBack = {}, onComplete = {})
}