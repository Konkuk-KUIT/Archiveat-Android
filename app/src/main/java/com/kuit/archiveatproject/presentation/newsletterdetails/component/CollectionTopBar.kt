package com.kuit.archiveatproject.presentation.newsletterdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun CollectionTopBar(
    userName: String,
    categoryLabel: String,  // 경제
    collectedCount: Int,    // 4
    monthLabel: String,     // 8월
    progressCurrent: Int,
    progressTotal: Int,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ArchiveatProjectTheme.colors.white)
            .padding(horizontal = 20.dp)
            .padding(top = 7.dp, bottom = 17.dp)
    ) {
        Row(){
            // 문구 3줄
            Text(
                text = "${userName}님이 저장한 ",
                style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                color = ArchiveatProjectTheme.colors.gray900,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${categoryLabel} ",
                style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                color = ArchiveatProjectTheme.colors.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "관련 콘텐츠",
                style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                color = ArchiveatProjectTheme.colors.gray900,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(){
            Text(
                text = "${collectedCount}개",
                style = ArchiveatProjectTheme.typography.Heading_2_bold,
                color = ArchiveatProjectTheme.colors.primary
            )
            Text(
                text = "를 모았어요",
                style = ArchiveatProjectTheme.typography.Heading_2_bold,
                color = ArchiveatProjectTheme.colors.gray900
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "${monthLabel}에 저장한 콘텐츠 기반",
            style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
            color = ArchiveatProjectTheme.colors.gray600
        )

        Spacer(Modifier.height(16.dp))

        CollectionProgressBar(
            checkedCount = progressCurrent,
            totalSteps = progressTotal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionTopBarPrev(){
    CollectionTopBar(
        userName = "OO",
        categoryLabel = "경제",
        collectedCount = 4,
        monthLabel = "8월",
        progressCurrent = 3,
        progressTotal = 4,
    )
}