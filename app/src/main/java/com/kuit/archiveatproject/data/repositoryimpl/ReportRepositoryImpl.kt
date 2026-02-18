package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toDomain
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.entity.ReportBalance
import com.kuit.archiveatproject.domain.entity.ReportInterestGap
import com.kuit.archiveatproject.domain.entity.ReportStatus
import com.kuit.archiveatproject.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ReportRepository {

    override suspend fun getReport(): Report {
        val response = apiService.getReport()
        val dto = response.requireData()
        return dto.copy(serverTimestamp = response.timestamp).toDomain()
    }

    override suspend fun getReportStatus(): ReportStatus {
        android.util.Log.d("ReportRepo", "getReportStatus() called")
        return apiService.getReportStatus()
            .requireData()
            .toDomain()
    }

    override suspend fun getReportBalance(): ReportBalance {
        return apiService.getReportBalance()
            .requireData()
            .toDomain()
    }

    override suspend fun getReportInterestGap(): List<ReportInterestGap> {
        return apiService.getReportInterestGap()
            .requireData()
            .topics
            .map { it.toDomain() }
    }
}
