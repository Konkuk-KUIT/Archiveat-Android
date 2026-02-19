package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.request.newsletter.NewsletterSaveRequestDto
import com.kuit.archiveatproject.data.dto.response.BaseResponse
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterDeleteResponseDto
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterDetailResponseDto
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterSaveResponseDto
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterSimpleResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NewsletterApiService {
    @DELETE("/newsletters/{userNewsletterId}")
    suspend fun deleteNewsletter(
        @Path("userNewsletterId") userNewsletterId: Long
    ): BaseResponse<NewsletterDeleteResponseDto>

    @GET("/newsletters/{userNewsletterId}")
    suspend fun getNewsletterDetail(
        @Path("userNewsletterId") userNewsletterId: Long
    ): BaseResponse<NewsletterDetailResponseDto>

    @GET("/newsletters/{userNewsletterId}/simple")
    suspend fun getNewsletterSimple(
        @Path("userNewsletterId") userNewsletterId: Long
    ): BaseResponse<NewsletterSimpleResponseDto>

    @POST("/newsletters")
    suspend fun saveNewsletter(
        @Body body: NewsletterSaveRequestDto
    ): BaseResponse<NewsletterSaveResponseDto>

    @PATCH("/newsletters/{userNewsletterId}")
    suspend fun patchNewsletterRead(
        @Path("userNewsletterId") userNewsletterId: Long
    ): BaseResponse<Unit>
}