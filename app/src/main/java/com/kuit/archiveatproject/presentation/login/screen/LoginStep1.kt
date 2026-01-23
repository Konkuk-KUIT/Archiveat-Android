package com.kuit.archiveatproject.presentation.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun LoginStep1(
    onStartWithEmail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(ArchiveatProjectTheme.colors.white)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 31.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "정보를 내 지식으로 만드는",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.gray800,
                textAlign = TextAlign.Center,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "첫걸음, ",
                    style = ArchiveatProjectTheme.typography.Heading_1_bold,
                    color = ArchiveatProjectTheme.colors.gray800,
                )

                Text(
                    text = "archiveat",
                    style = ArchiveatProjectTheme.typography.Logo_regular,
                    color = ArchiveatProjectTheme.colors.primary,
                )
            }
        }

        PrimaryRoundedButton(
            text = "이메일로 1초만에 시작하기",
            onClick = onStartWithEmail,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 14.dp),
            heightDp = 50,
            cornerRadiusDp = 50,
        )
    }
}

@Preview
@Composable
fun LoginStep1Prev() {
    LoginStep1(onStartWithEmail = {})
}