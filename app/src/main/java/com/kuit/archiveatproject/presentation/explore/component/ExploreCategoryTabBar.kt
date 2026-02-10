package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreCategoryTabItem
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreCategoryTabBar(
    categories: List<ExploreCategoryTabItem>,
    selectedCategoryId: Long,
    onCategorySelected: (categoryId: Long) -> Unit,
    modifier: Modifier = Modifier,
){
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
            val isSelected = category.id == selectedCategoryId

            ExploreCategoryTabItem(
                item = category,
                isSelected = isSelected,
                onClick = { onCategorySelected(category.id) }
            )
        }
    }
}

@Composable
private fun ExploreCategoryTabItem(
    item: ExploreCategoryTabItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val primaryColor = ArchiveatProjectTheme.colors.primary
    val inactiveColor = ArchiveatProjectTheme.colors.gray800

    Column(
        modifier = Modifier
            .noRippleClickable(onClick = onClick)
            .width(64.dp)
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = item.iconResId),
            contentDescription = item.name,
            tint = if (isSelected) primaryColor else inactiveColor,
            modifier = Modifier.size(28.dp)
        )

        Text(
            text = item.name,
            style = ArchiveatProjectTheme.typography.Body_1_semibold,
            color = if (isSelected) primaryColor else inactiveColor,
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 하단 선택 indicator
        Box(
            modifier = Modifier
                .height(2.dp)
                .width(41.dp)
                .background(
                    color = if (isSelected) primaryColor else Color.Transparent,
                    shape = RoundedCornerShape(1.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreCategoryTabBarPreview() {
    ExploreCategoryTabBar(
        categories = listOf(
            ExploreCategoryTabItem(1, "IT/과학", R.drawable.ic_category_it),
            ExploreCategoryTabItem(2, "국제", R.drawable.ic_category_internation),
            ExploreCategoryTabItem(3, "경제", R.drawable.ic_category_economy),
            ExploreCategoryTabItem(4, "문화", R.drawable.ic_category_culture),
            ExploreCategoryTabItem(5, "생활", R.drawable.ic_category_living),
            ),
        selectedCategoryId = 2,
        onCategorySelected = {},
    )
}