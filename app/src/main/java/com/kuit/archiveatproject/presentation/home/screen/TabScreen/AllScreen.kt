package com.kuit.archiveatproject.presentation.home.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.home.component.SubMessageComponent
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme


@Composable
fun AllScreen(
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .padding(start = 20.dp)
    ) {
        SubMessageComponent(
            "수집한 자료를 기반으로 발행된, 나만의 지식 뉴스레터",
            ArchiveatProjectTheme.colors.gray700
        )
    }
}