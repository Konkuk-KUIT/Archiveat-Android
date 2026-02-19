package com.kuit.archiveatproject.presentation.newsletterdetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.component.tag.ImageSource
import com.kuit.archiveatproject.core.component.tag.ImageTag
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.presentation.inbox.util.DomainLogoMapper
import com.kuit.archiveatproject.ui.theme.ArchiveatFontMedium
import com.kuit.archiveatproject.ui.theme.ArchiveatFontSemiBold
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

data class CollectionComponentUiModel(
    val id: Long,
    val categoryLabel: String,      // 경제정책
    val sourceLabel: String,        // Youtube
    val minutesLabel: String,       // 30분
    val thumbnailUrl: String,
    val title: String,
    val subtitle: String,
    val isChecked: Boolean,
)

@Composable
fun CollectionComponent(
    model: CollectionComponentUiModel,
    onClick: (Long) -> Unit, // id ->
    modifier: Modifier = Modifier,
//        .width(335.dp)
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(ArchiveatProjectTheme.colors.white)
            .border(
                width = 1.25.dp,
                color = ArchiveatProjectTheme.colors.gray200,
                shape
            )
            .clickable { onClick(model.id) }
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
        // 상단: 태그, 시간, 체크
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextTag( // 카테고리
                    text = model.categoryLabel,
                    variant = TagVariant.Custom,
                    modifier = Modifier.height(25.dp),
                    style = ArchiveatProjectTheme.typography.Caption_semibold,
                    verticalPadding = 4.dp
                )
                val logoRes = DomainLogoMapper.logoResIdOrNull(model.sourceLabel)
                ImageTag(
                    text = model.sourceLabel.toDisplayDomainName(),
                    icon = ImageSource.Res(logoRes ?: R.drawable.ic_link),
                    textStyle = ArchiveatProjectTheme.typography.Caption_medium_sec,
                    textColor = ArchiveatProjectTheme.colors.gray800,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ImageTag( // Nn분
                    text = model.minutesLabel,
                    icon = ImageSource.Res(R.drawable.ic_clock)
                )
                CheckButton(
                    checked = model.isChecked,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // 사진 제목/소제목
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            if (model.thumbnailUrl.isNotBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(model.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "thumbnail",
                    modifier = Modifier
                        .size(65.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(ArchiveatProjectTheme.colors.gray100),
                    contentScale = ContentScale.Crop
                )
            } else { // image url 없을 때
                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(ArchiveatProjectTheme.colors.gray100)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text = model.title,
                    style = TextStyle(
                        fontFamily = ArchiveatFontSemiBold,
                        fontSize = 16.sp,
                        lineHeight = 1.4.em, // 행간 140%
                        letterSpacing = 0.em,
                    ),
                    color = ArchiveatProjectTheme.colors.black,
                    /*maxLines = 3,
                    overflow = TextOverflow.Ellipsis,*/
                )
                Text(
                    text = model.subtitle,
                    style = TextStyle(
                        fontFamily = ArchiveatFontMedium,
                        fontSize = 12.sp,
                        lineHeight = 1.4.em, // 행간 140%
                        letterSpacing = 0.em,
                    ),
                    color = ArchiveatProjectTheme.colors.gray800,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

private fun String.toDisplayDomainName(): String {
    return when (this.lowercase()) {
        "tistory" -> "Tistory"
        "naver news" -> "Naver News"
        "youtube" -> "YouTube"
        else -> this
    }
}

@Composable
private fun CheckButton(
    checked: Boolean,
    modifier: Modifier = Modifier,
) {
    val checkedR = R.drawable.ic_check_filled
    val uncheckedR = R.drawable.ic_check_empty

    val resId = if (checked) checkedR else uncheckedR

    Image(
        painter = painterResource(id = resId),
        contentDescription = if (checked) "checked" else "unchecked",
        modifier = modifier
    )
}

@Preview(showBackground = false)
@Composable
private fun CollectionComponentPrev() {
    CollectionComponent(
        CollectionComponentUiModel(
            id = 1,
            categoryLabel = "경제정책",
            sourceLabel = "Youtube",
            minutesLabel = "30분",
            thumbnailUrl = "ㅇ",
            title = "비트코인 '4년 주기론' 끝나나…'업토버' 사라진 이유는 [한경 코알라]",
            isChecked = true,
            subtitle = "비트코인 팔 때 참고할 것"
        ),
        onClick = {}
    )
}
