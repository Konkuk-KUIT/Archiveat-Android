package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.request.InboxClassificationRequestDto
import com.kuit.archiveatproject.data.dto.request.UserMetadataSubmitRequestDto
import com.kuit.archiveatproject.data.dto.response.BaseResponse
import com.kuit.archiveatproject.data.dto.response.CollectionDetailsResponseDto
import com.kuit.archiveatproject.data.dto.response.HomeResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxEditResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicNewslettersResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.InboxClassificationResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportBalanceDto
import com.kuit.archiveatproject.data.dto.response.report.ReportInterestGapResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportStatusDto
import com.kuit.archiveatproject.data.dto.response.user.UserMetadataResponseDto
import com.kuit.archiveatproject.data.dto.response.user.UserNicknameResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // home
    @GET("/home")
    suspend fun getHome(): BaseResponse<HomeResponseDto>

    // explore
    @GET("/explore")
    suspend fun getExplore(): BaseResponse<ExploreResponseDto>

    @GET("/explore/inbox")
    suspend fun getExploreInbox(): BaseResponse<ExploreInboxResponseDto>

    @GET("/explore/topic/{topicId}/user-newsletters") // 토픽 별 뉴스레터 목록 조회
    suspend fun getTopicUserNewsletters(
        @Path("topicId") topicId: Long,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
    ): BaseResponse<ExploreTopicNewslettersResponseDto>

    @PATCH("/explore/inbox/{userNewsletterId}/classification")
    suspend fun patchInboxClassification(
        @Path("userNewsletterId") userNewsletterId: Long,
        @Body body: InboxClassificationRequestDto,
    ): BaseResponse<InboxClassificationResponseDto>

    @GET("/explore/inbox/{userNewsletterId}")
    suspend fun getExploreInboxEdit(
        @Path("userNewsletterId") userNewsletterId: Long
    ): BaseResponse<ExploreInboxEditResponseDto>

    /**
     * data: null
     */
    @PATCH("/explore/inbox/confirmation")
    suspend fun confirmExploreInboxAll(): BaseResponse<Unit>

    // report
    @GET("/report")
    suspend fun getReport(): BaseResponse<ReportResponseDto>

    @GET("/report/weekly/consumption")
    suspend fun getReportStatus(): BaseResponse<ReportStatusDto>

    @GET("/report/weekly/balance")
    suspend fun getReportBalance(): BaseResponse<ReportBalanceDto>

    @GET("/report/weekly/gap")
    suspend fun getReportInterestGap(): BaseResponse<ReportInterestGapResponseDto>

    // user
    @GET("/user/metadata")
    suspend fun getUserMetadata(): BaseResponse<UserMetadataResponseDto>

    @GET("/user/nickname")
    suspend fun getUserNickname(): BaseResponse<UserNicknameResponseDto>

    @POST("/user/metadata")
    suspend fun submitUserMetadata(
        @Body body: UserMetadataSubmitRequestDto,
    ): BaseResponse<Unit>

    // collection
    @GET("/collections/{collectionId}")
    suspend fun getCollectionDetails(
        @Path("collectionId") collectionId: Long,
    ): BaseResponse<CollectionDetailsResponseDto>
}
