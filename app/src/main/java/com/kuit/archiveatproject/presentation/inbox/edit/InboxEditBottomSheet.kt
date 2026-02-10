package com.kuit.archiveatproject.presentation.inbox.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.ui.theme.ArchiveatFontSemiBold
import com.kuit.archiveatproject.ui.theme.ArchiveatLogoFontRegular
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlin.math.roundToInt

@Composable
fun InboxEditBottomSheet(
    userNewsletterId: Long,
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InboxEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    /**
     * 이 화면이 열릴 때 / userNewsletterId가 바뀔 때 딱 1번씩 네트워크 fetch를 하려는 목적
     * userNewsletterId를 외부에서 파라미터로 받을 수 있으니까
     * VM이 가진 id와 지금 전달된 id가 다르면 fetch 다시 하도록
     */
    LaunchedEffect(userNewsletterId) {
        if (userNewsletterId != -1L && uiState.userNewsletterId != userNewsletterId) {
            viewModel.fetch(userNewsletterId)
        }
    }

    // 실제 화면은 Stateless UI로 분리, 여기서만 이벤트를 VM에 연결
    InboxEditOverlayContent(
        modifier = modifier,
        state = uiState,
        onDismiss = { // 메뉴 닫고
            viewModel.dismissMenu()
            onDismiss()
        },
        onBackgroundClick = { viewModel.dismissMenu() }, // 배경 클릭
        onClickCancel = { // 우상단 취소 클릭
            viewModel.dismissMenu()
            onDismiss()
        },
        onClickCategoryField = { viewModel.openCategoryMenu() }, // 필드 클릭
        onClickTopicField = { viewModel.openTopicMenu() }, // 필드 클릭
        onSelectCategory = { viewModel.selectCategory(it) }, // 항목 선택
        onSelectTopic = { viewModel.selectTopic(it) }, // 항목 선택
        onMemoChange = { viewModel.onMemoChange(it) },
        onMemoClick = { viewModel.dismissMenu() },
        onSave = { // 저장 버튼
            viewModel.save(
                onSaved = {
                    onSaved()
                    onDismiss()
                }
            )
        },
    )
}

/* -------------------- Stateless UI -------------------- */

@Composable
fun InboxEditOverlayContent(
    state: InboxEditUiState,
    // 뷰모델에서 주입
    onDismiss: () -> Unit,
    onBackgroundClick: () -> Unit,
    onClickCancel: () -> Unit,
    onClickCategoryField: () -> Unit,
    onClickTopicField: () -> Unit,
    onSelectCategory: (Long) -> Unit,
    onSelectTopic: (Long) -> Unit,
    onMemoChange: (String) -> Unit,
    onMemoClick: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier.navigationBarsPadding(),
) {
    val borderGray = Color(0xFFAAAAB3)
    val selectBg = Color(0xFFDFD7FF)
    val primaryPurple = ArchiveatProjectTheme.colors.deepPurple

    // 앵커(필드) 위치/크기 (Root 기준)
    var categoryAnchorPos by remember { mutableStateOf(Offset.Zero) }
    var categoryAnchorSize by remember { mutableStateOf(IntSize.Zero) }

    var topicAnchorPos by remember { mutableStateOf(Offset.Zero) }
    var topicAnchorSize by remember { mutableStateOf(IntSize.Zero) }

    val density = LocalDensity.current
    val gapPx = with(density) { 8.dp.toPx() }

    // 현재 선택된 id를 name으로 보여주기 / 없으면 "선택"
    val categoryName = state.selectedCategoryName().ifBlank { "선택" }
    val topicName = state.selectedTopicName().ifBlank { "선택" }

    // Root Overlay
    Box( // dim 배경
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.black.copy(alpha = 0.8f))
            .clickable(
                enabled = state.openMenu != OpenMenu.NONE,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onBackgroundClick() }
    ) {
        // 하단 패널
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 11.dp, bottom = 2.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 11.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "archiveat!",
                        color = ArchiveatProjectTheme.colors.deepPurple,
                        style = TextStyle(
                            fontFamily = ArchiveatLogoFontRegular,
                            fontSize = 24.sp,
                            lineHeight = 1.4.em,
                            letterSpacing = 0.01.sp,
                        )
                    )

                    Text(
                        text = "취소",
                        color = ArchiveatProjectTheme.colors.gray900,
                        style = TextStyle(
                            fontFamily = ArchiveatFontSemiBold,
                            fontSize = 16.sp,
                            lineHeight = 1.4.em,
                            letterSpacing = 0.em,
                        ),
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onClickCancel() }
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Category / Topic
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Label("Category")
                        Spacer(Modifier.height(8.dp))

                        val expanded = state.openMenu == OpenMenu.CATEGORY
                        DropdownField(
                            text = categoryName,
                            borderColor = if (expanded) primaryPurple else borderGray,
                            icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            iconTint = if (expanded) primaryPurple else ArchiveatProjectTheme.colors.gray400,
                            // 좌표/크기 저장(드롭다운 위치/너비 계산에 필요)
                            modifier = Modifier.onGloballyPositioned { coords ->
                                categoryAnchorPos = coords.positionInRoot()
                                categoryAnchorSize = coords.size
                            },
                            onClick = onClickCategoryField
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Label("Topic")
                        Spacer(Modifier.height(8.dp))

                        val expanded = state.openMenu == OpenMenu.TOPIC
                        DropdownField(
                            text = topicName,
                            borderColor = if (expanded) primaryPurple else borderGray,
                            icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            iconTint = if (expanded) primaryPurple else Color(0xFF8D8D98),
                            modifier = Modifier.onGloballyPositioned { coords ->
                                topicAnchorPos = coords.positionInRoot()
                                topicAnchorSize = coords.size
                            },
                            onClick = onClickTopicField
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Memo
                Label("Memo")
                Spacer(Modifier.height(8.dp))

                MemoInputBox(
                    value = state.memo,
                    onValueChange = onMemoChange,
                    placeholder = "(예: 주말에 읽을 마케팅 자료)",
                    borderColor = ArchiveatProjectTheme.colors.gray400,
                    onFocusLikeClick = onMemoClick, // 메모 터치 시 드롭다운 닫힘
                )

                Spacer(Modifier.height(16.dp))

                PrimaryRoundedButton( // 보관하기 버튼
                    text = if (state.isSaving) "저장 중..." else "보관하기",
                    onClick = { if (!state.isSaving) onSave() },
                    fullWidth = true,
                    cornerRadiusDp = 16,
                    heightDp = 46,
                    containerColor = ArchiveatProjectTheme.colors.deepPurple,
                )

                Spacer(Modifier.height(5.dp))

                // 에러 메시지
                state.errorMessage?.let { msg ->
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = msg,
                        color = ArchiveatProjectTheme.colors.sub_2,
                        style = ArchiveatProjectTheme.typography.Caption_medium_sec
                    )
                }
            }
        }

        // 드롭다운 오버레이: Root(Box) 위에 절대 위치로
        when (state.openMenu) {
            OpenMenu.CATEGORY -> {
                val items = state.categories.filter { it.id != null && it.name != null }
                DropdownOverlay(
                    anchorPos = categoryAnchorPos,
                    anchorSize = categoryAnchorSize,
                    items = items.map { it.id!! to it.name!! },
                    selectedId = state.selectedCategoryId,
                    onSelect = { onSelectCategory(it) },
                    indicatorColor = selectBg,
                    gapPx = gapPx
                )
            }

            OpenMenu.TOPIC -> {
                val items = state.filteredTopics()
                DropdownOverlay(
                    anchorPos = topicAnchorPos,
                    anchorSize = topicAnchorSize,
                    items = items.map { it.id to it.name },
                    selectedId = state.selectedTopicId,
                    onSelect = { onSelectTopic(it) },
                    indicatorColor = selectBg,
                    gapPx = gapPx
                )
            }

            OpenMenu.NONE -> Unit
        }
    }
}

/* -------------------- UI Parts -------------------- */

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = ArchiveatFontSemiBold,
            fontSize = 16.sp,
            lineHeight = 1.4.em,
            letterSpacing = 0.em,
        ),
        color = ArchiveatProjectTheme.colors.gray900,
    )
}

@Composable
private fun DropdownField(
    text: String,
    borderColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.25.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Body_2_semibold,
            color = ArchiveatProjectTheme.colors.gray900
        )
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
    }
}

/*
 * Root(Box) 위에 절대 위치로 띄우는 드롭다운
 * - x = 앵커 x
 * - y = 앵커 y + 앵커 높이 + 8dp
 * - width = 앵커 width
 * - height = 125dp (고정)
 * - 스크롤 인디케이터(0xFFDFD7FF) 항상 표시
 * - 메뉴 영역 클릭은 consume 처리(바깥 dismiss로 안 넘어가게)
 */
@Composable
private fun DropdownOverlay(
    anchorPos: Offset,
    anchorSize: IntSize,
    items: List<Pair<Long, String>>,
    selectedId: Long?,
    onSelect: (Long) -> Unit,
    indicatorColor: Color,
    gapPx: Float,
) {
    if (anchorSize.width == 0 || anchorSize.height == 0) return

    val listState = rememberLazyListState()
    val density = LocalDensity.current

    val xPx = anchorPos.x.roundToInt()
    val yPx = (anchorPos.y + anchorSize.height + gapPx).roundToInt()
    val widthDp = with(density) { anchorSize.width.toDp() }

    Box(
        modifier = Modifier
            .offset { IntOffset(xPx, yPx) }
            .width(widthDp)       // 필드 너비와 동일
            .height(125.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /* consume */ }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 0.dp,
            shadowElevation = 2.dp,
            color = ArchiveatProjectTheme.colors.white
        ) {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                        .padding(end = 10.dp)
                ) {
                    items(items.size) { idx ->
                        val (id, name) = items[idx]
                        DropdownItem(
                            text = name,
                            selected = id == selectedId,
                            selectedBg = indicatorColor,
                            onClick = { onSelect(id) }
                        )
                    }
                }

                PurpleScrollIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 6.dp),
                    listState = listState,
                    menuHeight = 125.dp,
                    indicatorColor = indicatorColor
                )
            }
        }
    }
}

@Composable
private fun DropdownItem(
    text: String,
    selected: Boolean,
    selectedBg: Color,
    onClick: () -> Unit,
) {
    val bg = if (selected) selectedBg else Color.Transparent
    val fc =
        if (selected) ArchiveatProjectTheme.colors.deepPurple else ArchiveatProjectTheme.colors.gray900

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 3.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = fc,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// 스크롤 인디케이터
@Composable
private fun PurpleScrollIndicator(
    modifier: Modifier,
    listState: LazyListState,
    menuHeight: Dp,
    indicatorColor: Color,
) {
    val trackHeight = menuHeight - 16.dp

    val total = listState.layoutInfo.totalItemsCount.coerceAtLeast(1)
    val visible = listState.layoutInfo.visibleItemsInfo.size.coerceAtLeast(1)

    val visibleRatio = (visible.toFloat() / total.toFloat()).coerceIn(0.12f, 1f)
    val indicatorHeight = (trackHeight * visibleRatio).coerceAtLeast(22.dp)

    val maxFirst = (total - visible).coerceAtLeast(1)
    val progress = (listState.firstVisibleItemIndex.toFloat() / maxFirst.toFloat()).coerceIn(0f, 1f)

    val maxOffset = (trackHeight - indicatorHeight).coerceAtLeast(0.dp)
    val offsetY = maxOffset * progress

    Box(
        modifier = modifier
            .width(3.dp)
            .height(trackHeight)
    ) {
        Box(
            modifier = Modifier
                .offset(y = offsetY)
                .fillMaxWidth()
                .height(indicatorHeight)
                .clip(RoundedCornerShape(999.dp))
                .background(indicatorColor)
        )
    }
}

@Composable
private fun MemoInputBox(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    borderColor: Color,
    onFocusLikeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp)
            .height(76.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.25.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onFocusLikeClick() } // 메모 박스 클릭 시 닫힘
            .padding(vertical = 10.dp, horizontal = 14.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = ArchiveatProjectTheme.typography.Caption_medium_sec.copy(
                color = ArchiveatProjectTheme.colors.gray950
            ),
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onFocusLikeClick() }, // 텍스트 필드 영역 클릭도 닫히게
            decorationBox = { inner ->
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        color = ArchiveatProjectTheme.colors.gray400,
                        style = ArchiveatProjectTheme.typography.Caption_medium_sec
                    )
                }
                inner()
            }
        )
    }
}

/* -------------------- Preview (VM 없이 확인) -------------------- */

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun InboxEditOverlay_Preview_TopicExpanded() {
    val dummy = remember {
        InboxEditUiState(
            isLoading = false,
            userNewsletterId = 123,
            categories = listOf(
                com.kuit.archiveatproject.domain.entity.InboxCategory(1, "경제"),
                com.kuit.archiveatproject.domain.entity.InboxCategory(2, "국제"),
                com.kuit.archiveatproject.domain.entity.InboxCategory(3, "IT/과학"),
            ),
            topics = listOf(
                com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(20, 1, "미국 주식"),
                com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(21, 1, "국내 주식"),
                com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(22, 1, "ETF"),
                com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(23, 1, "가상 화폐"),
                com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(10, 2, "지정학/외교"),
            ),
            selectedCategoryId = 1,
            selectedTopicId = 20,
            memo = "",
            openMenu = OpenMenu.TOPIC
        )
    }

    InboxEditOverlayContent(
        state = dummy,
        onDismiss = {},
        onBackgroundClick = {},
        onClickCancel = {},
        onClickCategoryField = {},
        onClickTopicField = {},
        onSelectCategory = {},
        onSelectTopic = {},
        onMemoChange = {},
        onMemoClick = {},
        onSave = {},
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun InboxEditOverlay_Preview_TopicCollapsed() {
    var state by remember {
        mutableStateOf(
            InboxEditUiState(
                isLoading = false,
                userNewsletterId = 123,
                categories = listOf(
                    com.kuit.archiveatproject.domain.entity.InboxCategory(1, "경제"),
                    com.kuit.archiveatproject.domain.entity.InboxCategory(2, "국제"),
                ),
                topics = listOf(
                    com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(20, 1, "미국 주식"),
                    com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(21, 1, "국내 주식"),
                    com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic(10, 2, "지정학/외교"),
                ),
                selectedCategoryId = 1,
                selectedTopicId = 20,
                memo = "나중에 다시 보기",
                openMenu = OpenMenu.NONE
            )
        )
    }

    InboxEditOverlayContent(
        state = state,
        onDismiss = {},
        onBackgroundClick = { state = state.copy(openMenu = OpenMenu.NONE) },
        onClickCancel = { state = state.copy(openMenu = OpenMenu.NONE) },
        onClickCategoryField = { state = state.copy(openMenu = if (state.openMenu == OpenMenu.CATEGORY) OpenMenu.NONE else OpenMenu.CATEGORY) },
        onClickTopicField = { state = state.copy(openMenu = if (state.openMenu == OpenMenu.TOPIC) OpenMenu.NONE else OpenMenu.TOPIC) },
        onSelectCategory = { cid ->
            val newTopics = state.topics.filter { it.categoryId == cid }
            state = state.copy(
                selectedCategoryId = cid,
                selectedTopicId = newTopics.firstOrNull()?.id,
                openMenu = OpenMenu.NONE
            )
        },
        onSelectTopic = { tid -> state = state.copy(selectedTopicId = tid, openMenu = OpenMenu.NONE) },
        onMemoChange = { state = state.copy(memo = it, openMenu = OpenMenu.NONE) },
        onMemoClick = { state = state.copy(openMenu = OpenMenu.NONE) },
        onSave = {},
    )
}
