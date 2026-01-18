package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.Report

interface ReportRepository {
    suspend fun getReport(): Report
}