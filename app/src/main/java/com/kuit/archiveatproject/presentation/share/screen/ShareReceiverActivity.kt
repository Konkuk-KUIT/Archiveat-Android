package com.kuit.archiveatproject.presentation.share.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.presentation.share.screen.ShareBottomSheet
import com.kuit.archiveatproject.presentation.share.viewmodel.ShareViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareReceiverActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = if (intent?.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
        } else ""

        setContent {
            ArchiveatProjectTheme {
                val viewModel: ShareViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(sharedText) {
                    viewModel.setContentUrlFromSharedText(sharedText)
                }

                ShareBottomSheet(
                    contentUrl = uiState.contentUrl,
                    onClose = { finish() },
                    onSave = { memo ->
                        viewModel.updateMemo(memo)
                        viewModel.saveNewsletter()
                    }
                )

                LaunchedEffect(uiState.isSuccess) {
                    if (uiState.isSuccess) {
                        finish()
                    }
                }
            }
        }
    }
}