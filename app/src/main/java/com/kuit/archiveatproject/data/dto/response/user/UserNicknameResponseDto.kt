package com.kuit.archiveatproject.data.dto.response.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNicknameResponseDto(
    @SerialName("nickname")
    val nickname: String,
)
