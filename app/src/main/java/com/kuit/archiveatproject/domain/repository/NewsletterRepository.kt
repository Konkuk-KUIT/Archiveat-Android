package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.NewsletterDetail
import com.kuit.archiveatproject.domain.entity.NewsletterSaveResult
import com.kuit.archiveatproject.domain.entity.NewsletterSimple

interface NewsletterRepository {
    suspend fun getNewsletterDetail(userNewsletterId: Long): NewsletterDetail
    suspend fun getNewsletterSimple(userNewsletterId: Long): NewsletterSimple

    suspend fun saveNewsletter(contentUrl: String, memo: String): NewsletterSaveResult

    suspend fun deleteNewsletter(userNewsletterId: Long): Long

    suspend fun patchNewsletterRead(userNewsletterId: Long)
}