package com.kuit.archiveatproject.presentation.report.component.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun ReportStatusBoxComponent(
    text: String,
    count: Int,
    textColor_1: Color,
    textColor_2: Color,
    boxColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier
){
    val textColor_1 = textColor_1
    val textColor_2 = textColor_2
    val boxColor = boxColor
    val borderColor = borderColor

    Box(
        modifier = modifier
            .size(width = 166.dp, height = 81.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(boxColor)
            .border(
                width = 1.25.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = text,
                style = ArchiveatProjectTheme.typography.Body_2_medium,
                color = textColor_1
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "${count}개",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = textColor_2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportStatusBoxComponentPreview(){
    Column {
        ReportStatusBoxComponent(
            text = "총 저장",
            count = 120,
            textColor_1 = ArchiveatProjectTheme.colors.gray600,
            textColor_2 = ArchiveatProjectTheme.colors.black,
            boxColor = ArchiveatProjectTheme.colors.gray50,
            borderColor = ArchiveatProjectTheme.colors.gray100
        )
        Spacer(Modifier.height(10.dp))
        ReportStatusBoxComponent(
            text = "읽음 완료",
            count = 42,
            textColor_1 = ArchiveatProjectTheme.colors.primary.copy(alpha = 0.7F),
            textColor_2 = ArchiveatProjectTheme.colors.primary,
            boxColor = ArchiveatProjectTheme.colors.primary.copy(alpha = 0.1F),
            borderColor = Color(0xFFE7DCFA)
        )
    }
}