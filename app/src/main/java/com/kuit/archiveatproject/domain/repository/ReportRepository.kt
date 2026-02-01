package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.entity.ReportBalance
import com.kuit.archiveatproject.domain.entity.ReportInterestGap
import com.kuit.archiveatproject.domain.entity.ReportStatus

interface ReportRepository {
    suspend fun getReport(): Report
    suspend fun getReportStatus(): ReportStatus
    suspend fun getReportBalance(): ReportBalance
    suspend fun getReportInterestGap(): List<ReportInterestGap>
}