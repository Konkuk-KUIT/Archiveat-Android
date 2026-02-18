package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.core.util.noRippleCircleClickable
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeThumbnailUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
private fun HomeContentThumbnail(
    thumbnails: List<HomeThumbnailUiModel>,
    fallbackDomainName: String?,
    modifier: Modifier = Modifier,
) {
    val visible = thumbnails.take(4)
    val extraCount = (thumbnails.size - 4).coerceAtLeast(0)

    val divider = 3.dp
    val dividerColor = ArchiveatProjectTheme.colors.gray200

    Box(modifier = modifier.background(ArchiveatProjectTheme.colors.primary)) {
        when (visible.size) {
            0 -> {
                DomainPlaceholder(
                    domainName = fallbackDomainName,
                    modifier = Modifier.fillMaxSize()
                )
            }

            1 -> {
                ThumbnailTile(thumbnail = visible[0], modifier = Modifier.fillMaxSize())
            }

            2 -> {
                Row(Modifier.fillMaxSize()) {
                    ThumbnailTile(
                        thumbnail = visible[0],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    Spacer(
                        Modifier
                            .width(divider)
                            .fillMaxHeight()
                            .background(dividerColor)
                    )
                    ThumbnailTile(
                        thumbnail = visible[1],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }

            3 -> {
                Row(Modifier.fillMaxSize()) {
                    ThumbnailTile(
                        thumbnail = visible[0],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    Spacer(
                        Modifier
                            .width(divider)
                            .fillMaxHeight()
                            .background(dividerColor)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        ThumbnailTile(
                            thumbnail = visible[1],
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        Spacer(
                            Modifier
                                .height(divider)
                                .fillMaxWidth()
                                .background(dividerColor)
                        )
                        ThumbnailTile(
                            thumbnail = visible[2],
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }
            }

            else -> {
                // 4개: 2x2 그리드
                Column(Modifier.fillMaxSize()) {
                    Row(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        ThumbnailTile(
                            thumbnail = visible[0], modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        Spacer(
                            Modifier
                                .width(divider)
                                .fillMaxHeight()
                                .background(dividerColor)
                        )
                        ThumbnailTile(
                            thumbnail = visible[1], modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                    Spacer(
                        Modifier
                            .height(divider)
                            .fillMaxWidth()
                            .background(dividerColor)
                    )
                    Row(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        ThumbnailTile(
                            thumbnail = visible[2], modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        Spacer(
                            Modifier
                                .width(divider)
                                .fillMaxHeight()
                                .background(dividerColor)
                        )

                        Box(
                            Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            ThumbnailTile(thumbnail = visible[3], modifier = Modifier.fillMaxSize())

                            // 5개 이상이면 마지막 타일에 +N 오버레이
                            if (extraCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.35f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+$extraCount",
                                        color = ArchiveatProjectTheme.colors.gray50,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ThumbnailTile(
    thumbnail: HomeThumbnailUiModel,
    modifier: Modifier = Modifier,
) {
    val url = thumbnail.thumbnailUrl?.takeIf { it.isNotBlank() }
    if (url != null) {
        TileImage(url = url, modifier = modifier)
    } else {
        DomainPlaceholder(domainName = thumbnail.domainName, modifier = modifier)
    }
}

@Composable
private fun DomainPlaceholder(
    domainName: String?,
    modifier: Modifier = Modifier,
) {
    val normalizedDomain = domainName?.trim()?.lowercase()
    val (backgroundColor, logoResId, description) = when (normalizedDomain) {
        "tistory" -> Triple(Color(0xFFFC5100), R.drawable.ic_logo_tistory, "tistory logo")
        "naver news" -> Triple(Color(0xFF2DB300), R.drawable.ic_logo_naver, "naver logo")
        "youtube" -> Triple(Color(0xFFE7211A), R.drawable.ic_logo_youtube, "youtube logo")
        else -> Triple(
            ArchiveatProjectTheme.colors.primary,
            R.drawable.ic_logo_simple,
            "default logo"
        )
    }

    Column(
        modifier = modifier.background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(logoResId),
            contentDescription = description,
            tint = Color.Unspecified,
        )
    }
}

@Composable
private fun TileImage(
    url: String,
    modifier: Modifier = Modifier,
) {
    // // // // //
    if (LocalInspectionMode.current) {
        // Preview에서는 네트워크 이미지 대신 더미 박스
        Box(modifier = modifier.background(ArchiveatProjectTheme.colors.primary))
        return
    }
    // // // // //

    AsyncImage(
        model = url,
        contentDescription = null,  // 서버에서 받아와야할 듯
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun HomeContentCard(
    card: HomeContentCardUiModel,
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    containerColor: Color = ArchiveatProjectTheme.colors.white,
    onClick: (HomeContentCardUiModel) -> Unit = {},
) {
    val shape = RoundedCornerShape(30.dp)

    val thumbnailsForThumb: List<HomeThumbnailUiModel> =
        if (card.thumbnails.isNotEmpty()) {
            card.thumbnails
        } else {
            card.imageUrls.map { imageUrl ->
                HomeThumbnailUiModel(
                    thumbnailUrl = imageUrl,
                    domainName = card.domainName
                )
            }
        }

    Column(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .then(
                if (isClickable)
                    Modifier.noRippleCircleClickable { onClick(card) }
                else Modifier
            )
            .fillMaxWidth()
    ) {
        HomeContentThumbnail(
            thumbnails = thumbnailsForThumb,
            fallbackDomainName = card.domainName,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(268f / 174f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 18.dp, top = 18.dp, end = 18.dp, bottom = 20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                TextTag(
                    text = card.tabLabel,
                    variant = TagVariant.Tab(type = card.tabType),
                )
                TextTag(
                    text = card.cardType.label,
                    variant = TagVariant.CardType(type = card.cardType)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = card.smallCardSummary,
                style = ArchiveatProjectTheme.typography.Caption_semibold,
                color = ArchiveatProjectTheme.colors.gray900,
                minLines = 1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(11.dp))

            Text(
                text = card.title,
                style = ArchiveatProjectTheme.typography.Subhead_1_bold,
                color = ArchiveatProjectTheme.colors.black,
                minLines = 1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(10.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val estimatedLineHeight = 20.dp
                val dynamicMaxLines = (maxHeight / estimatedLineHeight)
                    .toInt()
                    .coerceAtLeast(1)
                var frozenMaxLines by remember { mutableIntStateOf(0) }
                if (frozenMaxLines == 0 && dynamicMaxLines > 0) {
                    frozenMaxLines = dynamicMaxLines
                }
                val lineCount = if (frozenMaxLines > 0) frozenMaxLines else dynamicMaxLines

                Text(
                    text = card.mediumCardSummary,
                    modifier = Modifier.fillMaxWidth(),
                    minLines = lineCount,
                    maxLines = lineCount,
                    overflow = TextOverflow.Ellipsis,
                    style = ArchiveatProjectTheme.typography.Body_1_medium,
                    color = ArchiveatProjectTheme.colors.gray800
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentCardPrev() {
    ArchiveatProjectTheme {
        HomeContentCardPrevContent()
    }
}

@Composable
private fun HomeContentCardPrevContent() {
    val urls1 = listOf("https://picsum.photos/id/1/800/600")
    val urls2 = listOf(
        "https://picsum.photos/id/1/800/600",
        "https://picsum.photos/id/12/800/600"
    )
    val urls3 = listOf(
        "https://picsum.photos/id/1/800/600",
        "https://picsum.photos/id/14/800/600",
        "https://picsum.photos/id/15/800/600"
    )
    val urls4 = listOf(
        "https://picsum.photos/id/16/800/600",
        "https://picsum.photos/id/17/800/600",
        "https://picsum.photos/id/18/800/600",
        "https://picsum.photos/id/19/800/600"
    )
    val urls6 = listOf(
        "https://picsum.photos/id/20/800/600",
        "https://picsum.photos/id/21/800/600",
        "https://picsum.photos/id/22/800/600",
        "https://picsum.photos/id/23/800/600",
        "https://picsum.photos/id/24/800/600",
        "https://picsum.photos/id/25/800/600"
    )


    LazyColumn(
        Modifier.padding(top = 60.dp)
    ) {
        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 1,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "0장 케이스 (플레이스홀더)",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "이미지가 0장일 때 플레이스홀더가 나오는지 확인",
                    imageUrls = emptyList(),
                    domainName = ""
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }

        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 2,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "1장 케이스",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "1장일 때 단일 썸네일이 꽉 차는지 확인",
                    imageUrls = urls1
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }

        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 3,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "2장 케이스 (세로 2분할)",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "2장일 때 세로로 2분할 되는지 확인",
                    imageUrls = urls2
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }

        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 4,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "3장 케이스 (세로 3분할)",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "3장일 때 세로로 3분할 되는지 확인",
                    imageUrls = urls3
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }

        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 5,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "4장 케이스 (2x2 격자)",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "4장일 때 2x2로 나뉘는지 확인",
                    imageUrls = urls4
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }

        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 6,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "6장 케이스 (4장만 + 오버레이)",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "5장 이상일 때 4장만 쓰고 +n 표시되는지 확인",
                    imageUrls = urls6
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .size(width = 268.dp, height = 395.dp),
                onClick = {}
            )
        }

        item {
            HomeContentCard(
                card = HomeContentCardUiModel(
                    archiveId = 7,
                    tabType = HomeTabType.GROWTH,
                    tabLabel = "영감수집",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "fillMaxWidth() 케이스",
                    smallCardSummary = "저장한 'TechCrunch' 아티클 요약",
                    mediumCardSummary = "카드가 가로로 늘어났을 때도 이미지 분할이 자연스러운지 확인",
                    imageUrls = urls4
                ),
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                onClick = {}
            )
        }
    }
}
