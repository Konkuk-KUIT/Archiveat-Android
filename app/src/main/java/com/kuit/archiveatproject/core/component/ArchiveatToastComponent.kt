package com.kuit.archiveatproject.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ArchiveatToastComponent(
    message: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ArchiveatProjectTheme.colors.gray950)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_check_filled),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = message,
            color = ArchiveatProjectTheme.colors.white,
            modifier = Modifier.weight(1f)
        )

        actionText?.let {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = it,
                color = ArchiveatProjectTheme.colors.gray200,
                modifier = if (onActionClick != null) {
                    Modifier.clickable { onActionClick() }
                } else {
                    Modifier
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF2F2F2
)
@Composable
private fun ArchiveatToastComponentPreview() {
    ArchiveatProjectTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ArchiveatToastComponent(
                message = "'읽음'처리 되었어요!"
            )
        }
    }
}