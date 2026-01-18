package com.kuit.archiveatproject.presentation.share.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kuit.archiveatproject.presentation.share.screen.ShareBottomSheet
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

class ShareReceiverActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = if (intent?.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
        } else ""

        setContent {
            ArchiveatProjectTheme {
                ShareBottomSheet(
                    sharedText = sharedText,
                    onClose = { finish() },
                    onSave = { memo ->
                        // TODO 저장 처리
                        finish()
                    }
                )
            }
        }
    }
}
