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
fun GrowthScreen(
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .padding(start = 20.dp)
    ) {
        SubMessageComponent(
            "잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트",
            ArchiveatProjectTheme.colors.sub_1
        )
    }
}