package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterSimpleResponseDto
import com.kuit.archiveatproject.domain.entity.NewsletterSimpleResult

fun NewsletterSimpleResponseDto.toEntity(): NewsletterSimpleResult =
    NewsletterSimpleResult(
        isConfirmed = isConfirmed,
    )