package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.domain.entity.HomeTabType.INSPIRATION
import com.kuit.archiveatproject.presentation.home.util.color
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun CategoryTabItem(
    label: String,
    tabType: HomeTabType,
    selected: Boolean,
    onClick: () -> Unit
) {
    var textWidth by remember { mutableStateOf(0) }
    val tabColor = tabType.color()

    Column(
        modifier = Modifier
            .noRippleClickable {
                onClick()
            }
            .padding(horizontal = 10.dp, vertical = 12.dp)
    ) {
        Text(
            text = label,
            maxLines = 1,
            softWrap = false,
            style = ArchiveatProjectTheme.typography.Body_1_semibold,
            color = if (selected)
                tabColor
            else
                ArchiveatProjectTheme.colors.gray700,
            onTextLayout = { textLayoutResult ->
                textWidth = textLayoutResult.size.width
            }
        )

        if (selected && textWidth > 0) {
            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .width(with(LocalDensity.current) { textWidth.toDp() })
                    .height(2.5.dp)
                    .background(
                        color = tabColor
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryTabItemPreview()
{
    Row(
        modifier = Modifier,
        horizontalArrangement = spacedBy(10.dp)
    ){
        CategoryTabItem(
            label = "영감수집",
            tabType = INSPIRATION,
            selected = true,
            onClick = {}
        )
        CategoryTabItem(
            label = "전체",
            tabType = HomeTabType.ALL,
            selected = true,
            onClick = {}
        )
        CategoryTabItem(
            label = "집중탐구",
            tabType = HomeTabType.DEEP_DIVE,
            selected = true,
            onClick = {}
        )
        CategoryTabItem(
            label = "성장한입",
            tabType = HomeTabType.GROWTH,
            selected = true,
            onClick = {}
        )
        CategoryTabItem(
            label = "관점확장",
            tabType = HomeTabType.VIEW_EXPANSION,
            selected = true,
            onClick = {}
        )
    }

}