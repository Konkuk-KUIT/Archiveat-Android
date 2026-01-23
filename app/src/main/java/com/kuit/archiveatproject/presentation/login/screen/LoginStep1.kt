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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun LoginStep1(
    onStartWithEmail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val TiltWarpFamily = FontFamily(
        Font(R.font.tilt_warp_regular, weight = FontWeight.Normal)
    )

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
                    style = TextStyle(
                        fontFamily = TiltWarpFamily,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp,
                        lineHeight = 1.4.em,
                    ),
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