package com.kuit.archiveatproject.presentation.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun LoginStep1(
    onStartWithEmail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(ArchiveatProjectTheme.colors.white)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 31.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val density = LocalDensity.current

            Box(
                modifier = Modifier
                    .size(107.58594.dp)
                    .graphicsLayer(clip = false)
                    .drawBehind {
                        val iconSizePx = with(density) { 107.58594.dp.toPx() }

                        val blurPx = with(density) { 59.77.dp.toPx() }
                        val dxPx = with(density) { 0.dp.toPx() }
                        val dyPx = with(density) { 2.66.dp.toPx() }
                        val rPx = with(density) { 28.dp.toPx() }

                        val shadowColor = Color(0xFFCEBEF3)
                        val paint = android.graphics.Paint().apply {
                            isAntiAlias = true
                            color = android.graphics.Color.TRANSPARENT
                            setShadowLayer(
                                blurPx,
                                dxPx,
                                dyPx,
                                android.graphics.Color.argb(
                                    (shadowColor.alpha * 255).toInt(),
                                    (shadowColor.red * 255).toInt(),
                                    (shadowColor.green * 255).toInt(),
                                    (shadowColor.blue * 255).toInt(),
                                )
                            )
                        }

                        drawIntoCanvas { canvas ->
                            canvas.nativeCanvas.drawRoundRect(
                                0f,
                                0f,
                                iconSizePx,
                                iconSizePx,
                                rPx,
                                rPx,
                                paint
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "archiveat logo",
                    modifier = Modifier.size(107.58594.dp)
                )
            }

            Spacer(modifier = Modifier.height(34.41.dp))

            Text(
                text = "정보를 내 지식으로 만드는",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.gray800,
                textAlign = TextAlign.Center,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "첫걸음, ",
                    style = ArchiveatProjectTheme.typography.Heading_1_bold,
                    color = ArchiveatProjectTheme.colors.gray800,
                )

                Text(
                    text = "archiveat!",
                    style = ArchiveatProjectTheme.typography.Logo_regular,
                    color = ArchiveatProjectTheme.colors.primary,
                )
            }
        }

        PrimaryRoundedButton(
            text = "이메일로 1초만에 시작하기",
            onClick = onStartWithEmail,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 14.dp),
            heightDp = 50,
            cornerRadiusDp = 50,
        )
    }
}

@Preview
@Composable
fun LoginStep1Prev() {
    LoginStep1(onStartWithEmail = {})
}