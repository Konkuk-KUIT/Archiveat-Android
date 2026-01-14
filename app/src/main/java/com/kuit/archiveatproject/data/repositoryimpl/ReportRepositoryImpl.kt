package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toDomain
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.repository.ReportRepository

class ReportRepositoryImpl(
    private val apiService: ApiService
) : ReportRepository {

    override suspend fun getReport(): Report {
        return apiService.getReport().toDomain()
    }
}