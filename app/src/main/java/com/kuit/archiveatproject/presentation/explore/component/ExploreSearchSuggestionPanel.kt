package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreSearchSuggestionPanel(
    recommendedKeywords: List<String>,
    recentSearches: List<String>,
    onKeywordClick: (String) -> Unit,
    onRemoveRecent: (String) -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchPanelShadowFrame(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
                .background(
                    color = ArchiveatProjectTheme.colors.gray50,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .padding(horizontal = 15.dp, vertical = 17.dp)
        ) {

            // ===== 추천 검색어 =====
            Text(
                text = "추천 검색어",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray950
            )

            Spacer(Modifier.height(12.dp))

            recommendedKeywords.forEach { keyword ->
                Text(
                    text = "“$keyword”",
                    style = ArchiveatProjectTheme.typography.Body_1_semibold,
                    color = ArchiveatProjectTheme.colors.gray600,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .noRippleClickable { onKeywordClick(keyword) }
                )
            }

            Spacer(Modifier.height(24.dp))

            // ===== 검색 기록 헤더 =====
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "검색 기록",
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray700
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "전체 삭제",
                    style = ArchiveatProjectTheme.typography.Caption_medium,
                    color = ArchiveatProjectTheme.colors.gray600,
                    modifier = Modifier.noRippleClickable { onClearAll() }
                )
            }

            Spacer(Modifier.height(12.dp))

            // ===== 검색 기록 리스트 =====
            recentSearches.forEach { keyword ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            ArchiveatProjectTheme.colors.gray100,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 11.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = keyword,
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray600,
                        modifier = Modifier
                            .weight(1f)
                            .noRippleClickable { onKeywordClick(keyword) }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "삭제",
                        tint = ArchiveatProjectTheme.colors.gray400,
                        modifier = Modifier
                            .size(16.dp)
                            .noRippleClickable { onRemoveRecent(keyword) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchPanelShadowFrame(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    shadowColor: Color = Color.Black,
    shadowAlpha: Float = 0.25f,
    shadowBlur: Dp = 5.dp,
    shadowOffsetY: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .drawBehind {
                val paint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    color = android.graphics.Color.TRANSPARENT
                    setShadowLayer(
                        shadowBlur.toPx(),
                        0f,
                        shadowOffsetY.toPx(),
                        shadowColor.copy(alpha = shadowAlpha).toArgb()
                    )
                }

                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawRoundRect(
                        0f,
                        0f,
                        size.width,
                        size.height,
                        cornerRadius.toPx(),
                        cornerRadius.toPx(),
                        paint
                    )
                }
            }
    ) {
        content()
    }
}