package com.kuit.archiveatproject.presentation.newsletterdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun AIPreviewTextComponent(
    modifier: Modifier = Modifier,
    text: String,
    onClickMore: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.25.dp,
                color = ArchiveatProjectTheme.colors.gray100,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            text = text,
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = ArchiveatProjectTheme.colors.gray800,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(0.dp))
                .background(ArchiveatProjectTheme.colors.gray50)
                .clickable(onClick = onClickMore),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "자세히 보기",
                style = ArchiveatProjectTheme.typography.Body_2_medium,
                color = ArchiveatProjectTheme.colors.gray700
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun AIPreviewTextComponentPrev() {
    Box(modifier = Modifier.padding(20.dp)) {
        AIPreviewTextComponent(
            text = "불과 5년 전, 드리블(Dribbble)을 휩쓸었던 글래스모피즘이 돌아왔습니다. 하지만 이번엔 다릅니다. 애플 비전 프로가 쏘아 올린 '공간 UI'의 대중화로 인해, 평면적인 화면에서도 깊이감을 표현하는 것이 필수적인 UX 요소가 되었기 때문입니다.\n\n" +
                    "이번 테크크런치 아티클에서는 단순한 유행을 넘어, 개발자와 디자이너가 당장 실무에 적용해야 할 '기능적 글래스모피즘'의 3가지 법칙을 소개합니다. 특히 다크 모드에서의 명암비 해결법은 어쩌고 저쩌고 칸이 채워져있어야할듯요..." +
                    "불과 5년 전, 드리블(Dribbble)을 휩쓸었던 글래스모피즘이 돌아왔습니다. 하지만 이번엔 다릅니다. 애플 비전 프로가 쏘아 올린 '공간 UI'의 대중화로 인해, 평면적인 화면에서도 깊이감을 표현하는 것이 필수적인 UX 요소가 되었기 때문입니다.",
            onClickMore = {}
        )
    }
}

