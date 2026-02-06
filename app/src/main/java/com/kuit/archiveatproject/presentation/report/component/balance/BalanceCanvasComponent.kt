package com.kuit.archiveatproject.presentation.report.component.balance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.report.model.DepthAxis
import com.kuit.archiveatproject.presentation.report.model.KnowledgePosition
import com.kuit.archiveatproject.presentation.report.model.TimeAxis
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun BalanceCanvasComponent(
    position: KnowledgePosition,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        val size = min(maxWidth, maxHeight)
        val strokeWidth = 1.dp
        val dashEffect = PathEffect.dashPathEffect(
            floatArrayOf(6f, 6f),
            0f
        )
        val lineColor = ArchiveatProjectTheme.colors.gray100

        // ===== Canvas 영역 =====
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2
            val center = Offset(radius, radius)

            val labelPadding = 55.dp.toPx()
            val safeRadius = radius - labelPadding

            // 외곽 원
            drawCircle(
                color = lineColor,
                radius = radius,
                style = Stroke(width = strokeWidth.toPx())
            )

            // 세로 점선 (위)
            drawLine(
                color = lineColor,
                start = Offset(center.x, center.y - safeRadius),
                end = Offset(center.x, center.y),
                strokeWidth = strokeWidth.toPx(),
                pathEffect = dashEffect
            )

            // 세로 점선 (아래)
            drawLine(
                color = lineColor,
                start = Offset(center.x, center.y),
                end = Offset(center.x, center.y + safeRadius),
                strokeWidth = strokeWidth.toPx(),
                pathEffect = dashEffect
            )

            // 가로 점선 (좌)
            drawLine(
                color = lineColor,
                start = Offset(center.x - safeRadius, center.y),
                end = Offset(center.x, center.y),
                strokeWidth = strokeWidth.toPx(),
                pathEffect = dashEffect
            )

            // 가로 점선 (우)
            drawLine(
                color = lineColor,
                start = Offset(center.x, center.y),
                end = Offset(center.x + safeRadius, center.y),
                strokeWidth = strokeWidth.toPx(),
                pathEffect = dashEffect
            )
        }

        // ===== 사분면 라벨 =====
        // Future
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Future",
                style = ArchiveatProjectTheme.typography.Caption_medium.copy(
                    color = ArchiveatProjectTheme.colors.gray500,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "미래 준비",
                style = ArchiveatProjectTheme.typography.Caption_semibold.copy(
                    color = ArchiveatProjectTheme.colors.primary,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
        }

        // Now
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Now",
                style = ArchiveatProjectTheme.typography.Caption_medium.copy(
                    color = ArchiveatProjectTheme.colors.gray500,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "현재 필요",
                style = ArchiveatProjectTheme.typography.Caption_semibold.copy(
                    color = ArchiveatProjectTheme.colors.sub_2,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
        }

        // Light
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Light",
                style = ArchiveatProjectTheme.typography.Caption_medium.copy(
                    color = ArchiveatProjectTheme.colors.gray500,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "10분↓",
                style = ArchiveatProjectTheme.typography.Caption_semibold.copy(
                    color = ArchiveatProjectTheme.colors.sub_3,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
        }


        // Deep
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Deep",
                style = ArchiveatProjectTheme.typography.Caption_medium.copy(
                    color = ArchiveatProjectTheme.colors.gray500,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "10분↑",
                style = ArchiveatProjectTheme.typography.Caption_semibold.copy(
                    color = ArchiveatProjectTheme.colors.sub_1,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
        }


        // ===== 아이콘 위치 계산 =====
        val quadrantAlignment = when (position.timeAxis to position.depthAxis) {
            TimeAxis.NOW to DepthAxis.LIGHT -> Alignment.BottomStart
            TimeAxis.NOW to DepthAxis.DEEP -> Alignment.BottomEnd
            TimeAxis.FUTURE to DepthAxis.LIGHT -> Alignment.TopStart
            TimeAxis.FUTURE to DepthAxis.DEEP -> Alignment.TopEnd
            else -> Alignment.Center
        }

        // ===== 아이콘 =====
        Box(
            modifier = Modifier
                .align(quadrantAlignment)
                .padding(size * 0.25f)
                .size(39.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_report_balance),
                contentDescription = "Balance Icon",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BalanceCanvasComponentPreview() {
    ArchiveatProjectTheme {
        BalanceCanvasComponent(
            position = KnowledgePosition(
                timeAxis = TimeAxis.NOW,
                depthAxis = DepthAxis.DEEP
            ),
            modifier = Modifier.size(255.dp)
        )
    }
}