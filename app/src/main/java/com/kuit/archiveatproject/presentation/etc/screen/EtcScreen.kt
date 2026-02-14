package com.kuit.archiveatproject.presentation.etc.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.presentation.etc.viewmodel.EtcViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatFontRegular
import com.kuit.archiveatproject.ui.theme.ArchiveatFontSemiBold
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun EtcScreen(
    viewModel: EtcViewModel = hiltViewModel(),
    onLogoutSuccess: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLogoutSuccess) {
        if (uiState.isLogoutSuccess) {
            viewModel.consumeLogoutSuccess()
            onLogoutSuccess()
        }
    }

    EtcScreenContent(
        modifier = modifier,
        nickName = uiState.nickName,
        onLogoutClick = viewModel::logout
    )
}

@Composable
fun EtcScreenContent(
    modifier: Modifier = Modifier,
    nickName: String = "나",
    onLogoutClick: () -> Unit = {},
) {
    val nickNameFont = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 32.sp,
        lineHeight = 1.40.em,
        letterSpacing = 0.em,
    )
    val logOutFont = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 16.sp,
        lineHeight = 1.40.em,
        letterSpacing = 0.em,
        textDecoration = TextDecoration.Underline
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ArchiveatProjectTheme.colors.white)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "${nickName}님",
                style = nickNameFont,
                color = ArchiveatProjectTheme.colors.black
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "로그아웃",
                style = logOutFont,
                color = ArchiveatProjectTheme.colors.gray950,
                modifier = Modifier.noRippleClickable(onClick = onLogoutClick)
            )
        }
        Spacer(Modifier.height(15.dp))
        EtcComponent(text = "자주 묻는 질문")
        Spacer(Modifier.height(10.dp))
        EtcComponent(text = "공지사항")
        Spacer(Modifier.height(10.dp))
        EtcComponent(text = "앱 정보")

        Spacer(Modifier.weight(1f))
        PeopleComponent()
    }
}

@Composable
private fun EtcComponent(
    modifier: Modifier = Modifier,
    text: String,
) {
    val textFont = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 16.sp,
        lineHeight = 1.40.em,
        letterSpacing = 0.em,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = ArchiveatProjectTheme.colors.white,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = ArchiveatProjectTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = textFont,
            color = ArchiveatProjectTheme.colors.gray800
        )


        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = ArchiveatProjectTheme.colors.primary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun PeopleComponent(
    modifier: Modifier = Modifier,
){
    val roleFont = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 14.sp,
        lineHeight = 1.40.em,
        letterSpacing = 0.em,
    )
    val nameFont = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 10.sp,
        lineHeight = 1.40.em,
        letterSpacing = 0.em,
    )

    Column {
        Text(
            text = "archiveat!",
            style = ArchiveatProjectTheme.typography.Logo_regular,
            color = Color(0xFF262A3D)
        )
        Text(
            text = "을 만든 사람들",
            style = TextStyle(
                fontFamily = ArchiveatFontSemiBold,
                fontSize = 16.sp,
                lineHeight = 1.40.em,
                letterSpacing = 0.em,
            ),
            color = Color(0xFF383E53)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "PM",
            style = roleFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "임이준",
            style = nameFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "FE",
            style = roleFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "서아영, 사예원, 정지훈",
            style = nameFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "BE",
            style = roleFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "이준용, 고현규, 홍다연",
            style = nameFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "DES",
            style = roleFont,
            color = Color(0xFF383E53)
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "고경진, 김호정, 이은비",
            style = nameFont,
            color = Color(0xFF383E53)
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun EtcScreenPreview() {
    EtcScreenContent(
        nickName = "아영"
    )
}
