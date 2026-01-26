package com.kuit.archiveatproject.presentation.explore.model

import androidx.annotation.DrawableRes

data class ExploreTopTabUi(
    val id: String,
    val label: String,
    @DrawableRes val iconRes: Int
)

data class ExploreCategoryUi(
    val id: String,
    val title: String,
    val subtitle: String,
    @DrawableRes val iconRes: Int
)
