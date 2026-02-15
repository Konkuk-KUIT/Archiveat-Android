package com.kuit.archiveatproject.presentation.report.component.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

private const val MIN_WEIGHT = 0.0001f

private fun safeWeight(value: Float): Float {
    // RowScope.weight는 반드시 0보다 커야 함
    return when {
        value.isNaN() || value.isInfinite() -> MIN_WEIGHT
        value <= 0f -> MIN_WEIGHT
        else -> value
    }
}

@Composable
fun BalanceBarRow(
    title: String,
    leftLabel: String,
    leftPercent: Int,
    leftColor: Color,
    rightLabel: String,
    rightPercent: Int,
    rightColor: Color
) {
    val leftP = leftPercent.coerceIn(0, 100)
    val rightP = rightPercent.coerceIn(0, 100)
    val sum = leftP + rightP
    val leftRatio = if (sum == 0) 0.5f else leftP.toFloat() / sum.toFloat()
    val rightRatio = 1f - leftRatio
    val leftW = safeWeight(leftRatio)
    val rightW = safeWeight(rightRatio)

    Column {

        // 상단 타이틀
        Text(
            text = title,
            style = ArchiveatProjectTheme.typography.Caption_semibold,
            color = ArchiveatProjectTheme.colors.gray800
        )

        Spacer(Modifier.height(6.dp))

        // 퍼센트 텍스트
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_medium
                            .copy(color = ArchiveatProjectTheme.colors.gray500)
                            .toSpanStyle()
                    ) {
                        append("$leftLabel ")
                    }
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_semibold
                            .copy(color = ArchiveatProjectTheme.colors.gray500)
                            .toSpanStyle()
                    ) {
                        append("${leftP}%")
                    }
                }
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_medium
                            .copy(color = ArchiveatProjectTheme.colors.gray500)
                            .toSpanStyle()
                    ) {
                        append("$rightLabel ")
                    }
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_semibold
                            .copy(color = ArchiveatProjectTheme.colors.gray500)
                            .toSpanStyle()
                    ) {
                        append("${rightP}%")
                    }
                }
            )
        }

        Spacer(Modifier.height(6.dp))

        // 막대 그래프
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    color = ArchiveatProjectTheme.colors.gray100,
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(leftW)
                    .fillMaxHeight()
                    .background(
                        color = leftColor,
                        shape = RoundedCornerShape(
                            topStart = 4.dp,
                            bottomStart = 4.dp
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .weight(rightW)
                    .fillMaxHeight()
                    .background(
                        color = rightColor,
                        shape = RoundedCornerShape(
                            topEnd = 4.dp,
                            bottomEnd = 4.dp
                        )
                    )
            )
        }
    }
}
