package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicNewslettersResponseDto
import retrofit2.http.GET
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/explore")
    suspend fun getExplore(): ExploreResponseDto

    @GET("/explore/inbox")
    suspend fun getExploreInbox(): ExploreInboxResponseDto

    @GET("/explore/topic/{topicId}/user-newsletters")
    suspend fun getTopicUserNewsletters(
        @Path("topicId") topicId: Long,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
    ): ExploreTopicNewslettersResponseDto

    @GET("/report")
    suspend fun getReport(): ReportResponseDto
}