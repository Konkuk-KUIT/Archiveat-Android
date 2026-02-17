package com.kuit.archiveatproject.domain.entity

data class NewsletterDetail(
    val userNewsletterId: Long,
    val categoryName: String,
    val topicName: String,
    val title: String,
    val thumbnailUrl: String,
    val domainName: String?,
    val label: String,
    val memo: String,
    val contentUrl: String,
    val summary: List<NewsletterSummaryItem>
)

data class NewsletterSummaryItem(
    val title: String,
    val content: String
)

data class NewsletterSimple(
    val categoryName: String,
    val topicName: String,
    val title: String,
    val thumbnailUrl: String,
    val domainName: String?,
    val label: String,
    val memo: String,
    val contentUrl: String,
    val simpleSummary: List<NewsletterSimpleSummaryItem>
)

data class NewsletterSimpleSummaryItem(
    val title: String,
    val content: String
)

data class NewsletterSaveResult(
    val newsletterId: Long,
    val llmStatus: LlmStatus
)