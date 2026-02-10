package com.kuit.archiveatproject.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.noRippleCircleClickable
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun BackTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBack: () -> Unit,
    height: Int = 56,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ArchiveatProjectTheme.colors.white)
            .height(height.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back",
            tint = ArchiveatProjectTheme.colors.gray800,
            modifier = Modifier
                .size(20.dp)
                .noRippleCircleClickable { onBack() }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray950,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Preview(showBackground = false)
@Composable
fun BackTopBarPrev() {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        BackTopBar(onBack = {})
        BackTopBar(onBack = {}, title = "컴퓨터")
    }
}