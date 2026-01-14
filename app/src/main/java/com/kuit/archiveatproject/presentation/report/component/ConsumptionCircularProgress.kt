package com.kuit.archiveatproject.presentation.report.component

import android.R.attr.endColor
import android.R.attr.startColor
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ConsumptionCircularProgress(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val strokeWidth = 8.dp
    val sweepAngle = 360f * (percentage / 100f)

    val trackColor = ArchiveatProjectTheme.colors.gray50
    val gradientColors = listOf(
        Color(0xFF6220B8),
        Color(0xFF934EEC)
    )
    val textColor = ArchiveatProjectTheme.colors.gray600

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(50.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokePx = strokeWidth.toPx()
            val radius = (size.minDimension - strokePx) / 2
            val circumference = 2 * Math.PI * radius

            val rawSweepAngle = 360f * (percentage / 100f)

            val capAngle =
                ((strokePx / 2f) / circumference * 360f).toFloat()

            val adjustedSweepAngle =
                (rawSweepAngle - capAngle).coerceAtLeast(0f)

            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = strokePx,
                    cap = StrokeCap.Round
                )
            )

            rotate(-90f) {
                drawArc(
                    brush = Brush.sweepGradient(gradientColors),
                    startAngle = 0f,
                    sweepAngle = adjustedSweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokePx,
                        cap = StrokeCap.Round
                    )
                )
            }

        }

        Text(
            text = "${percentage}%",
            style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumptionCircularProgressPreview() {
    ConsumptionCircularProgress(
        percentage = 70,
        modifier = Modifier.padding(16.dp)
    )
}