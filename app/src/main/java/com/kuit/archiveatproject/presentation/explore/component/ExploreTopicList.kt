package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreTopicUiItem
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreTopicList(
    topics: List<ExploreTopicUiItem>,
    onTopicClick: (topicId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        topics.forEach { topic ->
            ExploreTopicItem(
                topic = topic,
                onClick = { onTopicClick(topic.id) }
            )
        }
    }
}

@Composable
fun ExploreTopicItem(
    topic: ExploreTopicUiItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ArchiveatProjectTheme.colors.gray50,
                shape = RoundedCornerShape(16.dp)
            )
            .noRippleClickable { onClick() }
            .padding(start = 15.dp, top = 17.dp, bottom = 17.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = topic.iconResId),
            contentDescription = null,
            tint = ArchiveatProjectTheme.colors.gray950,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = topic.name,
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray800
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = "${topic.newsletterCount}건의 아티클",
                style = ArchiveatProjectTheme.typography.Caption_semibold,
                color = ArchiveatProjectTheme.colors.gray600
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "right arrow",
            tint = ArchiveatProjectTheme.colors.primary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreTopicListPreview() {
    ArchiveatProjectTheme {
        ExploreTopicList(
            topics = listOf(
                ExploreTopicUiItem(1, "AI", 2, R.drawable.ic_topic_ai),
                ExploreTopicUiItem(2, "백엔드/인프라", 3, R.drawable.ic_topic_backend),
                ExploreTopicUiItem(3, "프론트/모바일", 4, R.drawable.ic_topic_front),
                ExploreTopicUiItem(4, "데이터/보안", 5, R.drawable.ic_topic_security),
                ExploreTopicUiItem(5, "테크", 6, R.drawable.ic_topic_tech),
            ),
            onTopicClick = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}