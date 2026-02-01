package com.kuit.archiveatproject.presentation.report.component.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.util.toRelativeDateText
import com.kuit.archiveatproject.presentation.report.model.RecentReadNewsletterUiItem
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun RecentNewsletterComponent(
    item: RecentReadNewsletterUiItem,
    serverTimestamp: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ArchiveatProjectTheme.colors.white)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(ArchiveatProjectTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 텍스트 영역
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusTextTag(
                    item.categoryName,
                    ArchiveatProjectTheme.colors.gray800
                )

                Text(
                    text = toRelativeDateText(
                        lastViewedAt = item.lastViewedAt,
                        serverTimestamp = serverTimestamp
                    ),
                    style = ArchiveatProjectTheme.typography.Caption_semibold,
                    color = ArchiveatProjectTheme.colors.gray400
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.title,
                style = ArchiveatProjectTheme.typography.Body_2_semibold,
                color = ArchiveatProjectTheme.colors.gray900,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentNewsletterComponentPreview() {
    ArchiveatProjectTheme {
        RecentNewsletterComponent(
            item = RecentReadNewsletterUiItem(
                id = 1054L,
                title = "2025 UI 디자인 트렌드: 글래스 모피즘일지 아닐지 한 번 맞춰보세요",
                categoryName = "디자인",
                lastViewedAt = "2026-01-25"
            ),
            serverTimestamp = "2026-01-25T13:14:06.480115",
            modifier = Modifier.padding(16.dp)
        )
    }
}