package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun GreetingBar(
    nickname: String,
    firstGreetingMessage: String,
    secondGreetingMessage: String,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ){
        Text(
            text = nickname + "님 $firstGreetingMessage",
            color = ArchiveatProjectTheme.colors.black,
            style = ArchiveatProjectTheme.typography.Heading_2_semibold
        )
        Text(
            text = secondGreetingMessage,
            color = ArchiveatProjectTheme.colors.black,
            style = ArchiveatProjectTheme.typography.Heading_2_semibold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingBarPreview(){
    GreetingBar("사예원", "좋은 아침이에요!",
        "오늘도 한 걸음 성장해볼까요?")
}