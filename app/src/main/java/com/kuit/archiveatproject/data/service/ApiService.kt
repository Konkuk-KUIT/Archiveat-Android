package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.response.ExploreResponseDto
import retrofit2.http.GET
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto

interface ApiService {
    @GET("/explore")
    suspend fun getExplore(): ExploreResponseDto

    @GET("/report")
    suspend fun getReport(): ReportResponseDto
}