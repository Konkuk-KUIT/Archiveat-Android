package com.kuit.archiveatproject.presentation.share.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareBottomSheet(
    contentUrl: String,
    onClose: () -> Unit,
    onSave: (memo: String) -> Unit,
) {
    var memo by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        dragHandle = null
    ) {
        ShareBottomSheetContent(
            memo = memo,
            onMemoChange = { memo = it },
            onClose = onClose,
            onSave = { onSave(memo) }
        )
    }
}

@Composable
private fun ShareBottomSheetContent(
    memo: String,
    onMemoChange: (String) -> Unit,
    onClose: () -> Unit,
    onSave: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(ArchiveatProjectTheme.colors.white)
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .navigationBarsPadding()
            .imePadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.archiveat_logo),
                contentDescription = "archiveat logo",
                modifier = Modifier.height(34.dp)
            )

            Text(
                text = "취소",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray900,
                modifier = Modifier.clickable { onClose() }
            )

        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Memo",
            style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
            color = ArchiveatProjectTheme.colors.gray900
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = memo,
            onValueChange = onMemoChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 76.dp),
            placeholder = {
                Text(
                    text = "(예: 주말에 읽을 마케팅 자료)",
                    style = ArchiveatProjectTheme.typography.Caption_medium,
                    color = ArchiveatProjectTheme.colors.gray400
                )
            },
            textStyle = ArchiveatProjectTheme.typography.Caption_medium.copy(
                color = ArchiveatProjectTheme.colors.gray900
            ),
            maxLines = 5,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ArchiveatProjectTheme.colors.gray400,
                unfocusedBorderColor = ArchiveatProjectTheme.colors.gray400,
                cursorColor = ArchiveatProjectTheme.colors.gray900
            )
        )

        Spacer(Modifier.height(18.dp))

        Image(
            painter = painterResource(id = R.drawable.send),
            contentDescription = "save",
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { onSave() }
        )
    }
}


@Preview(showBackground = false)
@Composable
private fun ShareBottomSheetContentPreview() {
    ArchiveatProjectTheme {
        ShareBottomSheetContent(
            memo = "",
            onMemoChange = {},
            onClose = {},
            onSave = {}
        )
    }
}