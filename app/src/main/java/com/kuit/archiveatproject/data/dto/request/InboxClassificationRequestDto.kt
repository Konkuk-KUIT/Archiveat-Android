package com.kuit.archiveatproject.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InboxClassificationRequestDto(
    @SerialName("categoryId")
    val categoryId: Long,
    @SerialName("topicId")
    val topicId: Long,
    @SerialName("memo")
    val memo: String,
)