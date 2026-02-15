package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
    val safeUrl = decodedUrl.takeIf {
        it.startsWith("http://") || it.startsWith("https://")
    }.orEmpty()
    val isPreview = LocalInspectionMode.current
    var lastRequestedUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            BackTopBar(
                modifier = Modifier.statusBarsPadding(),
                title = "",
                onBack = onBack,
                height = 45
            )
        }
    ) { innerPadding: PaddingValues ->
        if (safeUrl.isBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView,
                                url: String?,
                                favicon: android.graphics.Bitmap?
                            ) {
                                Log.d("WebViewScreen", "onPageStarted url=$url")
                            }

                            override fun onPageFinished(view: WebView, url: String?) {
                                Log.d("WebViewScreen", "onPageFinished url=$url")
                            }

                            override fun shouldOverrideUrlLoading(
                                view: WebView,
                                request: WebResourceRequest
                            ): Boolean {
                                val target = request.url
                                Log.d("WebViewScreen", "shouldOverrideUrlLoading url=$target")
                                val scheme = target.scheme.orEmpty()
                                if (scheme == "http" || scheme == "https") {
                                    if (target.host == "link.naver.com" && target.path?.contains("bridge") == true) {
                                        val bridged = target.getQueryParameter("url")
                                        if (!bridged.isNullOrBlank() &&
                                            (bridged.startsWith("http://") || bridged.startsWith("https://"))
                                        ) {
                                            view.loadUrl(bridged)
                                            return true
                                        }
                                    }
                                    return false
                                }

                                val fallbackUrl = target.getQueryParameter("url")
                                if (!fallbackUrl.isNullOrBlank() &&
                                    (fallbackUrl.startsWith("http://") || fallbackUrl.startsWith("https://"))
                                ) {
                                    view.loadUrl(fallbackUrl)
                                    return true
                                }

                                return try {
                                    val intent = Intent(Intent.ACTION_VIEW, target)
                                    context.startActivity(intent)
                                    true
                                } catch (_: Exception) {
                                    true
                                }
                            }
                        }
                        @SuppressLint("SetJavaScriptEnabled")
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.useWideViewPort = false
                        settings.loadWithOverviewMode = false
                        settings.userAgentString =
                            "Mozilla/5.0 (Linux; Android 12) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0 Mobile Safari/537.36"
                        if (lastRequestedUrl != safeUrl) {
                            loadUrl(safeUrl)
                            lastRequestedUrl = safeUrl
                        }
                    }
                },
                update = { webView ->
                    if (lastRequestedUrl != safeUrl) {
                        webView.loadUrl(safeUrl)
                        lastRequestedUrl = safeUrl
                    }
                },
                onRelease = { webView ->
                    webView.stopLoading()
                    webView.removeAllViews()
                    webView.clearHistory()
                    webView.destroy()
                },
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
