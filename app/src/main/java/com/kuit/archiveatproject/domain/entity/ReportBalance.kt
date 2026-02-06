package com.kuit.archiveatproject.domain.entity

data class ReportBalance(
    val lightPercentage: Int,
    val deepPercentage: Int,
    val nowPercentage: Int,
    val futurePercentage: Int,
    val patternTitle: String = "",
    val patternDescription: String = "",
    val patternQuote: String = ""
)