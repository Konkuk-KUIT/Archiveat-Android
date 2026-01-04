package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun SubMessageComponent(
    subMessage: String,
    colorType: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.5.dp))
            .background(ArchiveatProjectTheme.colors.gray50)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = subMessage,
            color = colorType,
            style = ArchiveatProjectTheme.typography.Caption_medium,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubMessagePreview(){
    SubMessageComponent("잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트",
        ArchiveatProjectTheme.colors.primary)
}