package com.kuit.archiveatproject.data.dto.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Signup, login, reissue 공통 */
@Serializable
data class AuthTokenResponseDto(
    @SerialName("accessToken")
    val accessToken: String,

    @SerialName("grantType")
    val grantType: String,
)