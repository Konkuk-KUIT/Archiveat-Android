package com.kuit.archiveatproject.presentation.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kuit.archiveatproject.presentation.explore.component.ExploreCategoryTabBar
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ){
        Row(
            modifier = Modifier
                .padding(top = 13.dp, bottom = 13.dp, start = 20.dp)
        ) {
            Text(
                text = "탐색",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.gray950
            )
        }
        ExploreCategoryTabBar(
            categories = uiState.categoryTabs,
            selectedCategoryId = uiState.selectedCategoryId,
            onCategorySelected = viewModel::onCategorySelected
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreScreenPreview(){
    ExploreScreen()
}