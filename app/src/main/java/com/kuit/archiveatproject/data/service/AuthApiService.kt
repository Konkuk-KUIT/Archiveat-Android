package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.request.auth.CheckEmailRequestDto
import com.kuit.archiveatproject.data.dto.request.auth.LoginRequestDto
import com.kuit.archiveatproject.data.dto.request.auth.SignupRequestDto
import com.kuit.archiveatproject.data.dto.response.BaseResponse
import com.kuit.archiveatproject.data.dto.response.auth.AuthTokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/auth/signup")
    suspend fun signup(
        @Body body: SignupRequestDto
    ): BaseResponse<AuthTokenResponseDto>

    @POST("/auth/login")
    suspend fun login(
        @Body body: LoginRequestDto
    ): BaseResponse<AuthTokenResponseDto>

    @POST("/auth/reissue")
    suspend fun reissue(): BaseResponse<AuthTokenResponseDto>

    @POST("/auth/logout")
    suspend fun logout(): BaseResponse<Unit>

    @POST("/auth/check-email")
    suspend fun checkEmail(
        @Body body: CheckEmailRequestDto
    ): BaseResponse<Boolean>
}