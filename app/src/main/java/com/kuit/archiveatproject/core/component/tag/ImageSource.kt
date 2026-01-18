package com.kuit.archiveatproject.core.component.tag

import androidx.annotation.DrawableRes

sealed class ImageSource {
    data class Url(val url: String) : ImageSource()
    data class Res(@DrawableRes val resId: Int) : ImageSource()
}