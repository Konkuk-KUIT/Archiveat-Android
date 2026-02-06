package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WebViewScreen(
    url: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val decodedUrl = Uri.decode(url)
    val isPreview = LocalInspectionMode.current

    Column(modifier = modifier.fillMaxSize()) {
        BackTopBar(
            title = "",
            onBack = onBack,
            height = 45
        )

        if (decodedUrl.isBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "유효한 링크를 찾을 수 없습니다.",
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray600
                )
            }
        } else if (isPreview) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WebView 보기",
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray600
                )
            }
        } else {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        loadUrl(decodedUrl)
                    }
                },
                update = { webView ->
                    if (webView.url != decodedUrl) {
                        webView.loadUrl(decodedUrl)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun WebViewScreenPreview() {
    ArchiveatProjectTheme {
        WebViewScreen(
            url = "https://n.news.naver.com/article/022/0004103735?cds=news_media_pc&type=editn",
            onBack = {}
        )
    }
}
