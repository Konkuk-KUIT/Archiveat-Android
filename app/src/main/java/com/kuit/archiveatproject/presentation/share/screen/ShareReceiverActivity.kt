package com.kuit.archiveatproject.presentation.share.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.kuit.archiveatproject.core.component.ArchiveatSnackbarHost
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class ShareReceiverActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = if (intent?.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
        } else ""

        setContent {
            ArchiveatProjectTheme {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                val sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                )

                Scaffold(
                    snackbarHost = { ArchiveatSnackbarHost(hostState = snackbarHostState) }
                ) { _ ->

                    ShareBottomSheet(
                        sheetState = sheetState,
                        sharedText = sharedText,
                        onClose = {
                            scope.launch {
                                sheetState.hide()
                                finish()
                            }
                        },
                        onSave = { memo ->
                            scope.launch {
                                sheetState.hide()

                                val result = snackbarHostState.showSnackbar(
                                    message = "저장이 완료됐어요",
                                    actionLabel = "보러가기",
                                    duration = SnackbarDuration.Short
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    val i = Intent(
                                        this@ShareReceiverActivity,
                                        com.kuit.archiveatproject.MainActivity::class.java
                                    ).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                        putExtra("open_saved", true)
                                    }
                                    startActivity(i)
                                }

                                finish()
                            }
                        }
                    )
                }
            }
        }
    }
}
