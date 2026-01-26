package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.explore.model.ExploreTopTabUi

@Composable
fun ExploreTopTabsRow(
    tabs: List<ExploreTopTabUi>,
    selectedTabId: String,
    onTabClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEach { tab ->
            ExploreTopTabItem(
                tab = tab,
                selected = tab.id == selectedTabId,
                onClick = { onTabClick(tab.id) }
            )
        }
    }
}

@Composable
private fun ExploreTopTabItem(
    tab: ExploreTopTabUi,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (selected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .width(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = tab.iconRes),
            contentDescription = tab.label,
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(tint)
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = tab.label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
            ),
            color = tint
        )

        Spacer(Modifier.height(6.dp))

        Surface(
            modifier = Modifier
                .height(3.dp)
                .fillMaxWidth(0.7f),
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(999.dp)
        ) {}
    }
}
