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
fun DeepDiveScreen(
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .padding(start = 20.dp)
    ) {
        SubMessageComponent(
            "관심 주제를 깊이 파고들어, 온전히 내 것으로 만드는 시간",
            ArchiveatProjectTheme.colors.sub_2
        )
    }
}