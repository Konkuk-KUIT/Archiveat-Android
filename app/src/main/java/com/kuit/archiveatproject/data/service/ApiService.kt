package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxResponseDto
import retrofit2.http.GET
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto

interface ApiService {
    @GET("/explore")
    suspend fun getExplore(): ExploreResponseDto

    @GET("/explore/inbox")
    suspend fun getExploreInbox(): ExploreInboxResponseDto

    @GET("/report")
    suspend fun getReport(): ReportResponseDto
}