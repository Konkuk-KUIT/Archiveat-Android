package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreInboxComponent(
    title: String,
    showLlmProcessingMessage: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = ArchiveatProjectTheme.colors.white,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFDADADA),
                shape = RoundedCornerShape(16.dp)
            )
            .noRippleClickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_inbox),
            contentDescription = null,
            tint = ArchiveatProjectTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray800
            )

            if (showLlmProcessingMessage) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "AI가 열심히 분류하고 있어요!",
                    style = ExploreLlmProcessingTextStyle,
                    color = ArchiveatProjectTheme.colors.gray800
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = ArchiveatProjectTheme.colors.primary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreNavigateCardPreviewRunning() {
    ArchiveatProjectTheme {
        ExploreInboxComponent(
            title = "방금 담은 지식",
            showLlmProcessingMessage = true,
            onClick = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreNavigateCardPreviewNormal() {
    ArchiveatProjectTheme {
        ExploreInboxComponent(
            title = "방금 담은 지식",
            showLlmProcessingMessage = false,
            onClick = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}

private val ExploreLlmProcessingTextStyle = androidx.compose.ui.text.TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    lineHeight = 1.45.sp,
    letterSpacing = (-0.002).em,
)