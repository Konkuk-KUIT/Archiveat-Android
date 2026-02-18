package com.kuit.archiveatproject.presentation.inbox.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.component.tag.ImageSource
import com.kuit.archiveatproject.core.component.tag.ImageTag
import com.kuit.archiveatproject.core.util.noRippleClickable
import com.kuit.archiveatproject.domain.entity.InboxCategory
import com.kuit.archiveatproject.domain.entity.InboxItem
import com.kuit.archiveatproject.domain.entity.InboxTopic
import com.kuit.archiveatproject.domain.entity.LlmStatus
import com.kuit.archiveatproject.presentation.inbox.util.DomainLogoMapper
import com.kuit.archiveatproject.presentation.inbox.util.InboxFormatters
import com.kuit.archiveatproject.ui.theme.ArchiveatFontSemiBold
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

fun String?.toDisplayDomainName(): String {
    return when (this?.lowercase()) {
        "tistory" -> "Tistory"
        "naver news" -> "Naver News"
        "youtube" -> "YouTube"
        else -> this ?: "웹사이트"
    }
}

@Composable
fun InboxItemComponent(
    item: InboxItem,
    onDelete: (Long) -> Unit,           // newsletterId
    onOpenOriginal: (Long) -> Unit,     // userNewsletterId
    onClickEdit: (InboxItem) -> Unit,   // 수정 바텀 시트 호출
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(16.dp)

    val isDone = item.llmStatus == LlmStatus.DONE
    // DONE일 때 카드 클릭 -> 원본 이동 (Action 버튼_3)
    val cardClickable = isDone

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(ArchiveatProjectTheme.colors.white)
            .border(1.25.dp, ArchiveatProjectTheme.colors.gray200, shape)
            .then(
                if (cardClickable) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onOpenOriginal(item.userNewsletterId) }
                } else Modifier
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row( // 상단: 태그 + 삭제
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isDone) {
                    // 로딩(또는 실패/대기/실행중) 상태
                    ImageTag(
                        text = "웹사이트",
                        icon = ImageSource.Res(R.drawable.ic_link),
                        textStyle = ArchiveatProjectTheme.typography.Etc_regular,
                        textColor = ArchiveatProjectTheme.colors.gray600,
                    )

                    Spacer(Modifier.width(8.dp))

                    val timeOrProcessing = InboxFormatters.ampmTime(item.createdAt) ?: "분석 중"

                    Row(
                        modifier = Modifier
                            .height(25.dp)
                            .clip(RoundedCornerShape(44.79.dp))
                            .background(ArchiveatProjectTheme.colors.gray50)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = timeOrProcessing,
                            style = ArchiveatProjectTheme.typography.Etc_regular,
                            color = ArchiveatProjectTheme.colors.gray600,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else {
                    // 완료 상태
                    val logoRes = DomainLogoMapper.logoResIdOrNull(item.domainName)
                    ImageTag(
                        text = item.domainName.toDisplayDomainName(),
                        icon = ImageSource.Res(logoRes ?: R.drawable.ic_link),
                        textStyle = ArchiveatProjectTheme.typography.Caption_medium_sec,
                        textColor = ArchiveatProjectTheme.colors.gray800,
                    )

                    Spacer(Modifier.width(8.dp))

                    val time = InboxFormatters.doneTimeLabel(item.createdAt).orEmpty()
                    if (time.isNotBlank()) {
                        Row(
                            modifier = Modifier
                                .height(25.dp)
                                .clip(RoundedCornerShape(44.79.dp))
                                .background(ArchiveatProjectTheme.colors.gray50)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                text = time,
                                style = ArchiveatProjectTheme.typography.Caption_medium_sec,
                                color = ArchiveatProjectTheme.colors.gray600,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                DeleteChip(
                    onClick = { onDelete(item.userNewsletterId) }
                )
            }

            // 중단: 제목 (로딩 = URL / 완료 = 일단 URL, 나중에 필드 따라 title 필드로 교체)
            if (!isDone) {
                Text(
                    text = item.contentUrl.orEmpty(),
                    style = ArchiveatProjectTheme.typography.Etc_regular,
                    color = ArchiveatProjectTheme.colors.gray500,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                val title = item.title.orEmpty()

                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = ArchiveatFontSemiBold,
                        fontSize = 16.sp,
                        lineHeight = 1.40.em,
                        letterSpacing = 0.em,
                    ),
                    color = ArchiveatProjectTheme.colors.black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // 하단: 로딩/완료 레이아웃 분기
            if (!isDone) {
                LoadingBottomRow()
            } else {
                DoneBottomRow(
                    categoryName = item.category?.name,
                    topicName = item.topic?.name,
                    onEdit = { onClickEdit(item) }
                )
            }
        }
    }
}

@Composable
private fun DeleteChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_delete),
        contentDescription = "삭제 버튼",
        modifier = modifier
            .size(24.dp)
            .noRippleClickable(onClick = onClick)
    )
}

@Composable
private fun LoadingBottomRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ArchiveatProjectTheme.colors.gray50)
            .padding(horizontal = 13.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp,
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = "내용을 읽고 있습니다",
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = ArchiveatProjectTheme.colors.black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_pen),
            contentDescription = "수정 버튼",
            modifier = Modifier
                .size(16.dp),
            alpha = 0.4f
        )
    }
}

@Composable
private fun DoneBottomRow(
    categoryName: String?,
    topicName: String?,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val label = listOfNotNull(
        categoryName?.takeIf { it.isNotBlank() },
        topicName?.takeIf { it.isNotBlank() }
    ).joinToString(" > ").ifBlank { "미분류" }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ArchiveatProjectTheme.colors.gray50)
            .padding(horizontal = 13.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = ArchiveatProjectTheme.colors.black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_pen),
            contentDescription = "수정 버튼",
            modifier = Modifier
                .size(16.dp)
                .noRippleClickable(onClick = onEdit)
        )
    }
}

// // // // //

@Preview(name = "InboxItemComponent - Loading", showBackground = true)
@Composable
private fun InboxItemComponentLoadingPreview() {
    ArchiveatProjectTheme {
        InboxItemComponent(
            item = InboxItem(
                userNewsletterId = 101,
                llmStatus = LlmStatus.RUNNING,
                contentUrl = "https://n.news.naver.com/article/028/0002787393?cds=news_media_pc",
                domainName = null,
                createdAt = "2026-01-18T14:30:00+09:00",
                category = null,
                topic = null,
                title = null,
            ),
            onDelete = {},
            onOpenOriginal = {},
            onClickEdit = {}
        )
    }
}

@Preview(name = "InboxItemComponent - Done")
@Composable
private fun InboxItemComponentDonePreview() {
    ArchiveatProjectTheme {
        InboxItemComponent(
            item = InboxItem(
                userNewsletterId = 102,
                llmStatus = LlmStatus.DONE,
                contentUrl = "\"돈도 기업도 한국을 떠난다\" 2026년 한국 경제가 진짜 무서운 이유 (김정호 교수)",
                domainName = "tistory",
                createdAt = "2026-01-18T14:30:00+09:00",
                category = InboxCategory(id = 1, name = "경제"),
                topic = InboxTopic(id = 1, name = "경제전망"),
                title = "\"돈도 기업도 한국을 떠난다\" 2026년 한국 경제가 진짜 무서운 이유 (김정호 교수)",
            ),
            onDelete = {},
            onOpenOriginal = {},
            onClickEdit = {}
        )
    }
}
