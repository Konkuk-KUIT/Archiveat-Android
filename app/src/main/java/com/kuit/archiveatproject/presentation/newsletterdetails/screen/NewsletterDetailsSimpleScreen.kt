package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel.NewsletterSimpleViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun NewsletterDetailsSimpleScreen(
    onBack: () -> Unit,
    onClickWebView: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsletterSimpleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.markRead()
    }

    val model = uiState.model
    if (model != null) {
        NewsletterDetailsAIContent(
            model = model,
            onBack = onBack,
            onClickWebView = { onClickWebView(uiState.contentUrl) },
            onClickDone = {},
            modifier = modifier,
            fromAI = false
        )
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
