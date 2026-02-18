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
import kotlin.math.roundToInt

private const val MIN_WEIGHT = 0.0001f

private fun safeWeight(value: Float): Float {
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
    rightColor: Color,
    leftLabelColor: Color = Color(0xFF7A5A00),
    rightLabelColor: Color = Color(0xFF1E4E9E),
) {
    val leftRaw = leftPercent.coerceAtLeast(0)
    val rightRaw = rightPercent.coerceAtLeast(0)

    val sum = leftRaw + rightRaw
    val leftRatio = if (sum > 0) leftRaw.toFloat() / sum.toFloat() else 0.5f
    val rightRatio = 1f - leftRatio

    val displayLeftP = if (sum > 0) (leftRatio * 100f).roundToInt().coerceIn(0, 100) else 0
    val displayRightP = if (sum > 0) 100 - displayLeftP else 0

    val leftW = if (leftRaw == 0) 0f else safeWeight(leftRatio)
    val rightW = if (rightRaw == 0) 0f else safeWeight(rightRatio)


    Column {
        Text(
            text = title,
            style = ArchiveatProjectTheme.typography.Caption_semibold,
            color = ArchiveatProjectTheme.colors.gray800
        )

        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_medium
                            .copy(color = leftLabelColor)
                            .toSpanStyle()
                    ) {
                        append("$leftLabel ")
                    }
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_semibold
                            .copy(color = leftLabelColor)
                            .toSpanStyle()
                    ) {
                        append("${displayLeftP}%")
                    }
                }
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_medium
                            .copy(color = rightLabelColor)
                            .toSpanStyle()
                    ) {
                        append("$rightLabel ")
                    }
                    withStyle(
                        ArchiveatProjectTheme.typography.Caption_semibold
                            .copy(color = rightLabelColor)
                            .toSpanStyle()
                    ) {
                        append("${displayRightP}%")
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
            when {
                leftRaw == 0 && rightRaw == 0 -> {
                }
                leftRaw == 0 -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(
                                color = rightColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }

                rightRaw == 0 -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(
                                color = leftColor,
                                shape = RoundedCornerShape(4.dp) // 양쪽 라운드
                            )
                    )
                }

                else -> {
                    val leftW = safeWeight(leftRatio)
                    val rightW = safeWeight(rightRatio)

                    Box(
                        modifier = Modifier
                            .weight(leftW)
                            .fillMaxHeight()
                            .background(
                                color = leftColor,
                                shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(rightW)
                            .fillMaxHeight()
                            .background(
                                color = rightColor,
                                shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                            )
                    )
                }
            }
        }

    }
}
