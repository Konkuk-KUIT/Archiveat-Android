package com.kuit.archiveatproject.presentation.newsletterdetails.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlin.math.max

@Composable
fun CollectionProgressBar(
    totalSteps: Int,    // 전체 카드/점 수
    checkedCount: Int,  // 체크된 카드 수
    modifier: Modifier = Modifier,
) {
    val baseSteps = max(0, totalSteps)
    val steps = baseSteps + 1
    val clampedChecked = checkedCount.coerceIn(0, baseSteps)

    // 표시 스텝을 +1 오프셋으로 사용한다.
    // 체크 0 -> 1번째 점, 체크 n -> (n+1)번째 점
    val selectedIndex = clampedChecked

    // progress: 0..1
    val targetProgress = if (steps == 1) 1f else selectedIndex.toFloat() / (steps - 1).toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "collectionProgress"
    )

    val trackColor = ArchiveatProjectTheme.colors.gray100
    val fillColor = ArchiveatProjectTheme.colors.primary
    val dotColor = ArchiveatProjectTheme.colors.gray400
    val selectedColor = Color(0xFF7745F5)
    val selectedBackColor = Color(0xFFC9C4FF)

    Canvas(modifier = modifier.height(20.dp)) {
        val w = size.width
        val h = size.height
        val cy = h / 2f

        val startX = 0f
        val endX = w
        val spacing = if (steps == 1)
            0f
        else
            (endX - startX) / (steps - 1)

        val stroke = 8.dp.toPx()
        val dotR = 3.dp.toPx()
        val selR = 4.dp.toPx()
        val haloR = 10.dp.toPx()

        // track
        drawLine(
            color = trackColor,
            start = Offset(startX, cy),
            end = Offset(endX, cy),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        // fill
        val fillEndX = startX + (endX - startX) * animatedProgress
        drawLine(
            color = fillColor,
            start = Offset(startX, cy),
            end = Offset(fillEndX, cy),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        // dots
        for (i in 0 until steps) {
            val x = startX + spacing * i

            val isCompletedOrSelected = i <= selectedIndex
            val c = if (isCompletedOrSelected) selectedColor
            else dotColor

            // 기본 점: 회색
            drawCircle(
                color = c,
                radius = dotR,
                center = Offset(x, cy)
            )
        }

        // 선택 점
        val selectedX = startX + spacing * selectedIndex
        drawCircle(
            color = selectedBackColor.copy(alpha = 0.6f),
            radius = haloR,
            center = Offset(selectedX, cy)
        )
        drawCircle(
            color = selectedColor,
            radius = selR,
            center = Offset(selectedX, cy)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CollectionProgressBarPreview() {
    ArchiveatProjectTheme {
        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // totalSteps=4 기준: 점 5개, checkedCount 0~4
            CollectionProgressBar(
                totalSteps = 4,
                checkedCount = 0,
                modifier = Modifier.fillMaxWidth()
            )
            CollectionProgressBar(
                totalSteps = 4,
                checkedCount = 1,
                modifier = Modifier.fillMaxWidth()
            )
            CollectionProgressBar(
                totalSteps = 4,
                checkedCount = 2,
                modifier = Modifier.fillMaxWidth()
            )
            CollectionProgressBar(
                totalSteps = 4,
                checkedCount = 3,
                modifier = Modifier.fillMaxWidth()
            )
            CollectionProgressBar(
                totalSteps = 4,
                checkedCount = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
