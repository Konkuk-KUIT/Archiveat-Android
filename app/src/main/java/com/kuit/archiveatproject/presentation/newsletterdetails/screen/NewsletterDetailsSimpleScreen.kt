package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.ArchiveatToastComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel.NewsletterSimpleViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlinx.coroutines.delay

@Composable
fun NewsletterDetailsSimpleScreen(
    onBack: () -> Unit,
    onClickWebView: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsletterSimpleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.model != null) {
        if (uiState.model != null) {
            viewModel.markReadOnEntryIfNeeded()
        }
    }

    LaunchedEffect(uiState.showReadToast) {
        if (!uiState.showReadToast) return@LaunchedEffect
        delay(2000)
        viewModel.dismissReadToast()
    }

    val model = uiState.model
    if (model != null) {
        Box(modifier = modifier.fillMaxSize()) {
            NewsletterDetailsAIContent(
                model = model,
                onBack = onBack,
                onClickWebView = { onClickWebView(uiState.contentUrl) },
                onClickDone = {},
                fromAI = false
            )
            if (uiState.showReadToast) {
                ArchiveatToastComponent(
                    message = "'읽음'처리 되었어요!",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val message = uiState.errorMessage ?: "로딩 중..."
            Text(
                text = message,
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray600
            )
        }
    }
}
