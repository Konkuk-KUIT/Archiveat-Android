package com.kuit.archiveatproject.presentation.share.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareBottomSheet(
    sheetState: SheetState,
    sharedText: String,
    onClose: () -> Unit,
    onSave: (memo: String) -> Unit,
) {
    var memo by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                TextButton(onClick = onClose) { Text("취소") }
            }

            Spacer(Modifier.height(10.dp))
            Text("Memo")
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = memo,
                onValueChange = { memo = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                placeholder = { Text("(예: 주말에 읽을 마케팅 자료)") },
                maxLines = 5
            )

            Spacer(Modifier.height(18.dp))

            Image(
                painter = painterResource(id = R.drawable.send),
                contentDescription = "save",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { onSave(memo) }
            )
        }
    }
}
