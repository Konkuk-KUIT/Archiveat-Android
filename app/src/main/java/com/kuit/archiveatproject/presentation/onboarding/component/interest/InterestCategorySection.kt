package com.kuit.archiveatproject.presentation.onboarding.component.interest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.domain.entity.UserMetadataCategory
import com.kuit.archiveatproject.domain.entity.UserMetadataTopic
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun InterestCategorySection(
    category: UserMetadataCategory,
    selectedTopicIds: Set<Long>,
    onTopicClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Text(
            text = category.name,
            style = ArchiveatProjectTheme.typography.Body_1_semibold,
            color = ArchiveatProjectTheme.colors.gray500
        )

        Spacer(Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(ArchiveatProjectTheme.colors.gray200)
        )

        Spacer(Modifier.height(14.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            category.topics.forEach { topic ->
                InterestChip(
                    text = topic.name,
                    selected = topic.id in selectedTopicIds,
                    onClick = { onTopicClicked(topic.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InterestCategorySectionPreview() {
    ArchiveatProjectTheme {
        InterestCategorySection(
            category = UserMetadataCategory(
                id = 1L,
                name = "IT/과학",
                topics = listOf(
                    UserMetadataTopic(id = 1L, name = "인공지능"),
                    UserMetadataTopic(id = 2L, name = "백엔드/인프라"),
                    UserMetadataTopic(id = 3L, name = "프론트엔드"),
                    UserMetadataTopic(id = 4L, name = "데이터분석"),
                    UserMetadataTopic(id = 5L, name = "클라우드")
                )
            ),
            selectedTopicIds = setOf(1L, 3L, 5L),
            onTopicClicked = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}