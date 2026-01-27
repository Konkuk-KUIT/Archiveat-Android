package com.kuit.archiveatproject.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

fun Modifier.bottomBorderWithRoundedCorners(
    color: Color,
    strokeWidth: Dp,
    bottomCornerRadius: Dp,
): Modifier = this.drawWithCache {
    val strokePx = strokeWidth.toPx()
    val r = bottomCornerRadius.toPx()
    val half = strokePx / 2f

    val w = size.width
    val h = size.height

    val left = half
    val top = half
    val right = w - half
    val bottom = h - half
    val rr = r.coerceAtMost(minOf((right - left) / 2f, (bottom - top) / 2f))

    val path = Path().apply {
        // 좌측 선 (위 -> 아래)
        moveTo(left, top)
        lineTo(left, bottom - rr)

        // 좌하단 라운드: 180(왼쪽) -> 90(아래) : sweep -90
        arcTo(
            rect = Rect(left, bottom - 2f * rr, left + 2f * rr, bottom),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )

        // 아래 선
        lineTo(right - rr, bottom)

        // 우하단 라운드: 90(아래) -> 0(오른쪽) : sweep는 -90
        arcTo(
            rect = Rect(right - 2f * rr, bottom - 2f * rr, right, bottom),
            startAngleDegrees = 90f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )

        // 우측 선 (아래 -> 위)
        lineTo(right, top)
    }

    onDrawBehind {
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokePx,
                cap = StrokeCap.Butt,
                join = StrokeJoin.Miter
            )
        )
    }
}


@Composable
fun ExploreTopicContentCard(
    title: String,
    thumbnailUrl: String?,
    isRead: Boolean,
    onClickCard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .height(188.dp)
            .clip(shape)
            .clickable { onClickCard() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (thumbnailUrl != null) {
                    AsyncImage(
                        model = thumbnailUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // TODO: 플랫폼 별 placeholder로 교체
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ArchiveatProjectTheme.colors.gray200)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(ArchiveatProjectTheme.colors.gray50)
                    .bottomBorderWithRoundedCorners(
                        color = ArchiveatProjectTheme.colors.gray100,
                        strokeWidth = 1.25.dp,
                        bottomCornerRadius = 16.dp
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp)
            ) {
                Text(
                    text = title,
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = ArchiveatProjectTheme.colors.gray700,
                )
            }
        }

        if (isRead) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFFD5DAE3).copy(alpha = 0.8f))
            )
        }

        if (isRead) {
            ReadBadge(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ReadBadge(
    modifier: Modifier = Modifier,
) {
    val badgeShape = RoundedCornerShape(999.dp)

    Row(
        modifier = modifier
            .clip(badgeShape)
            .background(ArchiveatProjectTheme.colors.gray400)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_check),
            contentDescription = null,
            tint = ArchiveatProjectTheme.colors.white
        )
        Text(
            text = "읽음",
            style = ArchiveatProjectTheme.typography.Caption_semibold,
            color = ArchiveatProjectTheme.colors.white
        )
    }
}

@Preview
@Composable
private fun ExploreContentCardPrev() {
    ExploreTopicContentCard(
        "알고리즘을 이해하다 · 추천 시스템 뒤에 숨은 계산법 fsadjl asfdjl fsd;l asfd",
        null,
        false,
        {},
        modifier = Modifier.size(188.dp)
    )
}