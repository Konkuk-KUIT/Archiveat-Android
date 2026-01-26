package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExploreSearchPanel(
    recommendedQueries: List<String>,
    history: List<String>,
    onClickRecommended: (String) -> Unit,
    onClickHistory: (String) -> Unit,
    onRemoveHistoryItem: (String) -> Unit,
    onClearAllHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(18.dp)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 추천 검색어
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "추천 검색어",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )

                recommendedQueries.forEach { item ->
                    Text(
                        text = "\"$item\"",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onClickRecommended(item) }
                            .padding(vertical = 6.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "검색 기록",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onClearAllHistory) {
                    Text("전체 삭제")
                }
            }

            if (history.isEmpty()) {
                Text(
                    text = "검색 기록이 없어요",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    history.forEach { h ->
                        HistoryChip(
                            text = h,
                            onClick = { onClickHistory(h) },
                            onRemove = { onRemoveHistoryItem(h) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryChip(
    text: String,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "remove",
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onRemove() }
            )
        }
    )
}
