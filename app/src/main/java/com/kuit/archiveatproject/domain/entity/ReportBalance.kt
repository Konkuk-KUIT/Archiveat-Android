package com.kuit.archiveatproject.domain.entity

data class ReportBalance(
    val lightPercentage: Int,
    val nowPercentage: Int
) {
    val heavyPercentage: Int
        get() = 100 - lightPercentage

    val longTermPercentage: Int
        get() = 100 - nowPercentage
}