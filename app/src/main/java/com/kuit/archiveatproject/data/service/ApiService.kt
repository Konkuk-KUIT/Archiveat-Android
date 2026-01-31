package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.request.InboxClassificationRequestDto
import com.kuit.archiveatproject.data.dto.request.UserMetadataSubmitRequestDto
import com.kuit.archiveatproject.data.dto.response.BaseResponse
import com.kuit.archiveatproject.data.dto.response.CollectionDetailsResponseDto
import com.kuit.archiveatproject.data.dto.response.HomeResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicNewslettersResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.InboxClassificationResponseDto
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterSimpleResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import com.kuit.archiveatproject.data.dto.response.user.UserMetadataResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/home")
    suspend fun getHome(): BaseResponse<HomeResponseDto>

    @GET("/explore")
    suspend fun getExplore(): BaseResponse<ExploreResponseDto>

    @GET("/explore/inbox")
    suspend fun getExploreInbox(): BaseResponse<ExploreInboxResponseDto>

    @GET("/explore/topic/{topicId}/user-newsletters")
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

    @GET("/report")
    suspend fun getReport(): BaseResponse<ReportResponseDto>

    @GET("/user/metadata")
    suspend fun getUserMetadata(): BaseResponse<UserMetadataResponseDto>

    @POST("/user/metadata")
    suspend fun submitUserMetadata(
        @Body body: UserMetadataSubmitRequestDto,
    ): BaseResponse<Unit>

    @PATCH("/newsletters/{userNewsletterId}")
    suspend fun patchNewsletterRead(
        @Path("userNewsletterId") userNewsletterId: Long,
    ): BaseResponse<Unit>

    @PATCH("/newsletters/{userNewsletterId}/simple")
    suspend fun patchNewsletterSimple(
        @Path("userNewsletterId") userNewsletterId: Long,
    ): BaseResponse<NewsletterSimpleResponseDto>

    @GET("/collections/{collectionId}")
    suspend fun getCollectionDetails(
        @Path("collectionId") collectionId: Long,
    ): BaseResponse<CollectionDetailsResponseDto>
}