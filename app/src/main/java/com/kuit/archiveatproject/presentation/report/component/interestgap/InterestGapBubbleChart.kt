package com.kuit.archiveatproject.presentation.report.component.interestgap

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.takeOrElse
import com.kuit.archiveatproject.core.util.noRippleCircleClickable
import com.kuit.archiveatproject.presentation.report.model.InterestGapTopicUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatFontRegular
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

private data class BubblePadding(
    val top: Dp,
    val bottom: Dp,
    val left: Dp,
    val right: Dp,
)

private fun BubblePadding.diameterIn(containerW: Dp, containerH: Dp): Dp {
    val w = containerW - left - right
    val h = containerH - top - bottom
    return min(w, h)
}

@Composable
fun InterestGapBubbleChart(
    topicsTop4: List<InterestGapTopicUiModel>,
    selectedTopicId: Long?,
    onSelectTopic: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val designW = 327.dp
    val designH = 300.dp

    val purple = BubblePadding(top = 79.dp, bottom = 95.dp, left = 91.dp, right = 109.dp)
    val orange = BubblePadding(top = 6.dp, bottom = 184.dp, left = 182.dp, right = 37.dp)
    val blue = BubblePadding(top = 190.dp, bottom = 27.dp, left = 39.dp, right = 205.dp)
    val yellow = BubblePadding(top = 193.dp, bottom = 38.dp, left = 190.dp, right = 67.dp)

    val p1 = topicsTop4.getOrNull(0)
    val p2 = topicsTop4.getOrNull(1)
    val p3 = topicsTop4.getOrNull(2)
    val p4 = topicsTop4.getOrNull(3)

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        // 부모가 주는 최대 폭
        val availableW = maxWidth

        // scale: 작은 폭이면 줄이고, 크면 1배
        val scale = minOf(1f, (availableW / designW))

        // 스케일된 컨테이너 크기
        val boxW = designW * scale
        val boxH = designH * scale

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ){
            Box(
                modifier = Modifier
                    .size(boxW, boxH), // 작은 폰
            ) {
                p1?.let { t ->
                    val d = purple.diameterIn(designW, designH) * scale
                    BubbleItem(
                        title = t.bubbleTitle,
                        value = "+${t.gap}",
                        size = d,
                        bg = ArchiveatProjectTheme.colors.primary,
                        content = ArchiveatProjectTheme.colors.primary,
                        isSelected = selectedTopicId == t.id,
                        onClick = { onSelectTopic(t.id) },
                        titleBaseStyle = ArchiveatProjectTheme.typography.Heading_1_bold,
                        valueStyle = ArchiveatProjectTheme.typography.Subhead_2_medium,
                        modifier = Modifier.absoluteOffset(
                            x = purple.left * scale,
                            y = purple.top * scale
                        )
                    )
                }

                p2?.let { t ->
                    val d = orange.diameterIn(designW, designH) * scale
                    BubbleItem(
                        title = t.bubbleTitle,
                        value = "+${t.gap}",
                        size = d,
                        bg = ArchiveatProjectTheme.colors.sub_2,
                        content = Color(0xFFDB654B),
                        isSelected = selectedTopicId == t.id,
                        onClick = { onSelectTopic(t.id) },
                        titleBaseStyle = ArchiveatProjectTheme.typography.Subhead_1_bold,
                        valueStyle = ArchiveatProjectTheme.typography.Subhead_2_medium,
                        modifier = Modifier.absoluteOffset(
                            x = orange.left * scale,
                            y = orange.top * scale
                        )
                    )
                }

                p3?.let { t ->
                    val d = blue.diameterIn(designW, designH) * scale
                    val body2Regular = ArchiveatProjectTheme.typography.Body_2_medium.copy(
                        fontFamily = ArchiveatFontRegular
                    )
                    BubbleItem(
                        title = t.bubbleTitle,
                        value = "+${t.gap}",
                        size = d,
                        bg = ArchiveatProjectTheme.colors.sub_1,
                        content = ArchiveatProjectTheme.colors.sub_1,
                        isSelected = selectedTopicId == t.id,
                        onClick = { onSelectTopic(t.id) },
                        titleBaseStyle = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                        valueStyle = body2Regular,
                        modifier = Modifier.absoluteOffset(
                            x = blue.left * scale,
                            y = blue.top * scale
                        )
                    )
                }

                p4?.let { t ->
                    val d = yellow.diameterIn(designW, designH) * scale
                    val body2Regular = ArchiveatProjectTheme.typography.Body_2_medium.copy(
                        fontFamily = ArchiveatFontRegular
                    )
                    BubbleItem(
                        title = t.bubbleTitle,
                        value = "+${t.gap}",
                        size = d,
                        bg = ArchiveatProjectTheme.colors.sub_3,
                        content = ArchiveatProjectTheme.colors.gray700,
                        isSelected = selectedTopicId == t.id,
                        onClick = { onSelectTopic(t.id) },
                        titleBaseStyle = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                        valueStyle = body2Regular,
                        modifier = Modifier.absoluteOffset(
                            x = yellow.left * scale,
                            y = yellow.top * scale
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun BubbleItem(
    title: String,
    value: String,
    size: Dp,
    bg: Color,
    content: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    titleBaseStyle: TextStyle,
    valueStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (isSelected) bg.copy(alpha = 0.4f) else Color.Transparent
    val titleStyle = autoFitTitleTextStyle(
        title = title,
        size = size,
        base = titleBaseStyle
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(bg.copy(alpha = 0.1f), CircleShape)
            .border(1.25.dp, borderColor, CircleShape)
            .noRippleCircleClickable(
                onClick = onClick
            )
    ) {
        Column(
            modifier = Modifier.background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = content,
                style = titleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = value,
                color = ArchiveatProjectTheme.colors.gray500,
                style = valueStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun autoFitTitleTextStyle(title: String, size: Dp, base: TextStyle): TextStyle {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val horizontalPadding = 10.dp
    val maxWidthPx = with(density) { (size - horizontalPadding * 2).roundToPx() }
        .coerceAtLeast(1)

    val maxFontSize = base.fontSize.takeOrElse { 16.sp }
    val minFontSize = 9.sp
    val startStep = (maxFontSize.value * 2f).toInt()
    val endStep = (minFontSize.value * 2f).toInt()

    var fitted = minFontSize
    for (step in startStep downTo endStep) {
        val candidate = (step / 2f).sp
        val result = textMeasurer.measure(
            text = AnnotatedString(title),
            style = base.copy(fontSize = candidate),
            maxLines = 1,
            overflow = TextOverflow.Clip,
            constraints = Constraints(maxWidth = maxWidthPx)
        )
        if (!result.hasVisualOverflow) {
            fitted = candidate
            break
        }
    }

    return base.copy(fontSize = fitted)
}

@Preview(showBackground = true)
@Composable
private fun InterestGapBubbleChartPrev() {
    InterestGapBubbleChart(
        listOf(
            InterestGapTopicUiModel(1, "경제", 24, 2),
            InterestGapTopicUiModel(2, "IT/Tech", 20, 10),
            InterestGapTopicUiModel(3, "인문", 10, 0),
            InterestGapTopicUiModel(4, "디자인", 2, 0)
        ),
        1,
        {}
    )
}
