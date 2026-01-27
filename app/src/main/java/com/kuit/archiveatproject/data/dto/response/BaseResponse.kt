package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("isSuccess")
    val isSuccess: Boolean,

    @SerialName("statusCode")
    val statusCode: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: T? = null,

    @SerialName("timestamp")
    val timestamp: String
)
