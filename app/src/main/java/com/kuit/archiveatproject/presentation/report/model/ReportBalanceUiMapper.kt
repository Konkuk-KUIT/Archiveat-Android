package com.kuit.archiveatproject.presentation.report.model

import com.kuit.archiveatproject.domain.entity.ReportBalance

fun ReportBalance.toUiState(): ReportBalanceUiState =
    ReportBalanceUiState(
        lightPercentage = lightPercentage,
        deepPercentage = deepPercentage,
        nowPercentage = nowPercentage,
        futurePercentage = futurePercentage,
        patternTitle = patternTitle,
        patternDescription = patternDescription,
        patternQuote = patternQuote
    )
