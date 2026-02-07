package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                color = ArchiveatProjectTheme.colors.gray50,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            enabled = enabled,
            singleLine = true,
            textStyle = ArchiveatProjectTheme.typography.Caption_medium.copy(
                color = ArchiveatProjectTheme.colors.gray950
            ),
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "기억나지 않는 아티클을 검색해보세요",
                        style = ArchiveatProjectTheme.typography.Caption_medium,
                        color = ArchiveatProjectTheme.colors.gray500
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "검색",
            tint = ArchiveatProjectTheme.colors.gray500,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable { onSearchClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreSearchBarPreview() {
    ArchiveatProjectTheme {
        ExploreSearchBar(
            query = "",
            onQueryChange = {},
            onSearchClick = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}