package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterDetailResponseDto
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterSaveResponseDto
import com.kuit.archiveatproject.data.dto.response.newsletter.NewsletterSimpleResponseDto
import com.kuit.archiveatproject.domain.entity.NewsletterDetail
import com.kuit.archiveatproject.domain.entity.NewsletterSaveResult
import com.kuit.archiveatproject.domain.entity.NewsletterSimple
import com.kuit.archiveatproject.domain.entity.NewsletterSimpleSummaryItem
import com.kuit.archiveatproject.domain.entity.NewsletterSummaryItem

fun NewsletterDetailResponseDto.toEntity(): NewsletterDetail =
    NewsletterDetail(
        userNewsletterId = userNewsletterId,
        categoryName = categoryName,
        topicName = topicName,
        title = title,
        thumbnailUrl = thumbnailUrl,
        label = label,
        memo = memo,
        contentUrl = contentUrl,
        summary = newsletterSummary.map { NewsletterSummaryItem(it.title, it.content) }
    )

fun NewsletterSimpleResponseDto.toEntity(): NewsletterSimple =
    NewsletterSimple(
        categoryName = categoryName,
        topicName = topicName,
        title = title,
        thumbnailUrl = thumbnailUrl,
        label = label,
        memo = memo,
        contentUrl = contentUrl,
        simpleSummary = newsletterSimpleSummary.map {
            NewsletterSimpleSummaryItem(
                it.title,
                it.content
            )
        }
    )

fun NewsletterSaveResponseDto.toEntity(): NewsletterSaveResult =
    NewsletterSaveResult(
        newsletterId = newsletterId,
        llmStatus = llmStatus.toEntity()
    )