package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreTopicFilterChip(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .border(
                width = 1.25.dp,
                color = ArchiveatProjectTheme.colors.gray200,
                shape = RoundedCornerShape(999.dp),
            )
            .clickable(enabled = enabled) { onClick() }
            .background(ArchiveatProjectTheme.colors.white)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Body_2_semibold,
            color = ArchiveatProjectTheme.colors.gray500,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            tint = ArchiveatProjectTheme.colors.gray400,
        )
    }
}

@Preview
@Composable
private fun ExploreFilterChipPrev() {
    ExploreTopicFilterChip(text = "시간", onClick = {})
}