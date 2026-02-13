package com.kuit.archiveatproject.presentation.newsletterdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun MemoComponent(
    memo: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "메모",
            style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
            color = ArchiveatProjectTheme.colors.gray800
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(ArchiveatProjectTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = ArchiveatProjectTheme.colors.gray100,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .padding(end = 24.dp)
                .fillMaxWidth()
        ){
            Text(
                text = memo,
                style = ArchiveatProjectTheme.typography.Body_2_medium,
                color = ArchiveatProjectTheme.colors.gray700
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MemoComponentPrev(){
    MemoComponent(
        memo = "짧아용"
    )
}