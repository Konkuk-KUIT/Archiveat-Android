package com.kuit.archiveatproject.presentation.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.TopLogoBar
import com.kuit.archiveatproject.presentation.home.component.GreetingBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TopLogoBar()
        }
        item {
            GreetingBar(
                "archiveat",
                "좋은 아침이에요!",
                "오늘도 한 걸음 성장해볼까요?"
                )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun HomeScreenPreview(){
    HomeScreen()
}