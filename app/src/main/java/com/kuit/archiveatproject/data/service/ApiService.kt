package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import retrofit2.http.GET

interface ApiService {

    @GET("report")
    suspend fun getReport(): ReportResponseDto
}