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
fun ViewExtensionScreen(
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .padding(start = 20.dp)
    ) {
        SubMessageComponent(
            "생각의 크기를 키워주는 깊이 있는 통찰",
            ArchiveatProjectTheme.colors.sub_3
        )
    }
}