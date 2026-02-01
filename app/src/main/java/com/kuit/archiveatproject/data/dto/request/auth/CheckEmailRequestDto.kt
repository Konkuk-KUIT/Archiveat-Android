package com.kuit.archiveatproject.data.dto.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckEmailRequestDto(
    @SerialName("email")
    val email: String,
)
