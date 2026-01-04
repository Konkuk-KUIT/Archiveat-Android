package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    @SerialName("firstGreetingMessage")
    val firstGreetingMessage: String,

    @SerialName("secondGreetingMessage")
    val secondGreetingMessage: String,

    @SerialName("tabs")
    val tabs: List<HomeTabDto>,

    @SerialName("contentCards")
    val contentCards: List<HomeContentCardDto>
)
