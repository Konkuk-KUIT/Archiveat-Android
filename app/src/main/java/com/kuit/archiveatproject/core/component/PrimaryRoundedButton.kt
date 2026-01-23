package com.kuit.archiveatproject.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun PrimaryRoundedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    fullWidth: Boolean = true,
    cornerRadiusDp: Int = 16,
    heightDp: Int = 50,
    horizontalPaddingDp: Int = 16,
    containerColor: Color = ArchiveatProjectTheme.colors.primary,
    contentColor: Color = ArchiveatProjectTheme.colors.white,
    leading: (@Composable (() -> Unit))? = null,
) {
    val shape = RoundedCornerShape(cornerRadiusDp.dp)
    val actualEnabled = enabled && !isLoading

    Button(
        onClick = onClick,
        enabled = actualEnabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .height(heightDp.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPaddingDp.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                } else if (leading != null) {
                    Box(
                        modifier = Modifier.size(18.dp),
                        contentAlignment = Alignment.Center
                    ) { leading() }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}


@Preview
@Composable
private fun PrimaryRoundedButtonPrev() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PrimaryRoundedButton(
            text = "이메일로 1초만에 시작하기",
            onClick = {},
        )
        PrimaryRoundedButton(
            text = "이메일로 1초만에 시작하기",
            onClick = {},
            isLoading = true
        )
        PrimaryRoundedButton(
            text = "다음",
            onClick = {},
        )
        PrimaryRoundedButton(
            text = "이전",
            onClick = {},
            contentColor = ArchiveatProjectTheme.colors.gray600,
            containerColor = ArchiveatProjectTheme.colors.gray200,
            heightDp = 100
        )
    }
}