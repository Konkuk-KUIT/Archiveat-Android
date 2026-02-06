package com.kuit.archiveatproject.presentation.report.component.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun StatusTextTag(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
){
    val textColor = textColor
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(45.dp))
            .background(ArchiveatProjectTheme.colors.gray50)
            .border(
                width = 1.dp,
                color = ArchiveatProjectTheme.colors.gray50,
                shape = RoundedCornerShape(45.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Caption_semibold,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusTextTagPreview(){
    StatusTextTag("이준님의 지식 좌표", ArchiveatProjectTheme.colors.primary)
}