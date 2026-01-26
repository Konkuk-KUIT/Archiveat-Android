package com.kuit.archiveatproject.presentation.inbox.util

import com.kuit.archiveatproject.R

object DomainLogoMapper {
    fun logoResIdOrNull(domainName: String?): Int? {
        val key = domainName?.lowercase()?.trim() ?: return null
        return when {
            "youtube" in key -> R.drawable.ic_logo_youtube
            "naver" in key -> R.drawable.ic_logo_naver
            "tistory" in key -> R.drawable.ic_logo_tistory
            else -> null
        }
    }
}