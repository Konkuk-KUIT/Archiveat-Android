package com.kuit.archiveatproject.presentation.report.component.status

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.ui.theme.ArchiveatFontBold
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun CircularProgressComponent(
    percentage: Int,
    modifier: Modifier = Modifier
){
    val textStyle = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 36.6.sp,
        lineHeight = 1.em,
        letterSpacing = (-0.002).em,
    )

    val sizeDp = 137.dp
    val strokeWidth = 17.dp

    val safePercentage = percentage.coerceIn(0, 100)

    val trackColor = ArchiveatProjectTheme.colors.gray50
    val textColor = ArchiveatProjectTheme.colors.gray600

    val startColor = Color(0xFF6220B8)
    val endColor = Color(0xFF934EEC)

    Box(
        modifier = modifier.size(sizeDp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {

            val strokePx = strokeWidth.toPx()
            val sweepAngle = 360f * (safePercentage / 100f)

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

            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(startColor, endColor),
                    start = Offset(size.width / 2f, 0f),   // 위
                    end = Offset(size.width / 2f, size.height) // 아래
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = strokePx,
                    cap = StrokeCap.Round
                )
            )
        }

        Text(
            text = "${safePercentage}%",
            style = textStyle,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CircularProgressComponentPreview() {
    CircularProgressComponent(
        percentage = 70,
        modifier = Modifier.padding(16.dp)
    )
}