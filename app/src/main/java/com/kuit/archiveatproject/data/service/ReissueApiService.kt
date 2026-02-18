package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.response.BaseResponse
import com.kuit.archiveatproject.data.dto.response.auth.AuthTokenResponseDto
import retrofit2.http.Header
import retrofit2.http.POST

interface ReissueApiService {
    @POST("/auth/reissue")
    suspend fun reissue(
        @Header("Cookie") cookie: String? = null,
    ): BaseResponse<AuthTokenResponseDto>
}
