package com.kuit.archiveatproject.presentation.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kuit.archiveatproject.presentation.login.uistate.LoginAgreementItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.core.util.noRippleCircleClickable
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun LoginStep2(
    agreementItems: List<LoginAgreementItem>,
    isLoading: Boolean,
    onBack: () -> Unit,
    onToggleAll: () -> Unit,
    onToggleItem: (id: String) -> Unit,
    onNext: () -> Unit,
    onClickDetail: (id: String) -> Unit = {}, // 나중에 약관 자세히 보기
    modifier: Modifier = Modifier,
) {
    val allChecked = agreementItems.all { it.checked }
    val requiredChecked = agreementItems.filter { it.required }.all { it.checked }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackTopBar(onBack = onBack, title = "", height = 56)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(top = 22.dp)
        ) {
            Text(
                text = "이용약관에\n동의해주세요",
                style = ArchiveatProjectTheme.typography.Heading_1_semibold,
                color = ArchiveatProjectTheme.colors.gray900
            )

            Spacer(modifier = Modifier.height(53.dp))

            // 전체 동의
            AgreementAllRow(
                checked = allChecked,
                onToggle = onToggleAll
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 1.dp),
                color = ArchiveatProjectTheme.colors.gray100
            )
            Spacer(modifier = Modifier.height(15.dp))

            // 개별 항목
            agreementItems.forEachIndexed { index, item ->
                AgreementRow(
                    required = item.required,
                    title = item.title,
                    checked = item.checked,
                    onToggle = {
                        onToggleItem(item.id)
                    },
                    onDetail = { onClickDetail(item.id) }
                )
                if (index != agreementItems.lastIndex) Spacer(modifier = Modifier.height(15.dp))
            }
        }

        PrimaryRoundedButton(
            text = "다음",
            // 나중에 서버와 연결 후 AgreementItem 및 onClick 달라질수도
            onClick = onNext,
            enabled = requiredChecked && !isLoading,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp),
            cornerRadiusDp = 12,
            heightDp = 50,
            containerColor = ArchiveatProjectTheme.colors.deepPurple
        )
    }
}

@Composable
private fun AgreementAllRow(
    checked: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleCircleClickable { onToggle() }
            .padding(bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AgreementAllCheck(checked = checked)
        Spacer(modifier = Modifier.width(12.dp))
        Row {
            Text(
                text = "전체 동의",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray950
            )
            Text(
                text = " (선택항목 포함)",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray400
            )
        }
    }
}

@Composable
private fun AgreementRow(
    required: Boolean,
    title: String,
    checked: Boolean,
    onToggle: () -> Unit,
    onDetail: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AgreementCheck(checked = checked)

        Spacer(modifier = Modifier.padding(start = 17.dp))

        Text(
            text = if (required) "[필수] " else "[선택] ",
            style = ArchiveatProjectTheme.typography.Body_2_semibold,
            color = ArchiveatProjectTheme.colors.gray950,
        )
        Text(
            text = title,
            style = ArchiveatProjectTheme.typography.Body_2_semibold,
            color = ArchiveatProjectTheme.colors.gray950,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AgreementAllCheck(
    checked: Boolean,
) {
    val bg =
        if (checked) ArchiveatProjectTheme.colors.primary else ArchiveatProjectTheme.colors.gray200
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(bg)
            .size(25.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = "전체 동의 체크",
            tint = ArchiveatProjectTheme.colors.white,
            modifier = Modifier.size(15.dp)
        )
    }
}

@Composable
private fun AgreementCheck(
    checked: Boolean,
) {
    val tintColor =
        if (checked) ArchiveatProjectTheme.colors.primary else ArchiveatProjectTheme.colors.gray200
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = "개별 항목 체크",
            tint = tintColor,
            modifier = Modifier.size(15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginStep2Preview() {
    LoginStep2(
        agreementItems = listOf(
            LoginAgreementItem("age14", "만 14세 이상입니다.", required = true, checked = true),
            LoginAgreementItem("terms", "서비스 이용약관 동의", required = true, checked = true),
            LoginAgreementItem("privacy", "개인정보 수집 및 이용 동의", required = true, checked = false),
            LoginAgreementItem("marketingUse", "마케팅 활용 동의", required = false, checked = false),
            LoginAgreementItem("marketingReceive", "마케팅 정보 수신 동의", required = false, checked = false),
        ),
        isLoading = false,
        onBack = {},
        onToggleAll = {},
        onToggleItem = {},
        onNext = {},
    )
}
