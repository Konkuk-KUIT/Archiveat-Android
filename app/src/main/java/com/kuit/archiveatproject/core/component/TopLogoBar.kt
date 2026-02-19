package com.kuit.archiveatproject.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun TopLogoBar(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ArchiveatProjectTheme.colors.white)
            .padding(start = 20.dp, bottom = 11.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "logo"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopLogoBarPreview(){
    TopLogoBar()
}