package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.NewsletterSimpleResult

interface NewsletterRepository {
    // 상세 읽음 처리
    suspend fun patchNewsletterRead(userNewsletterId: Long): Result<Unit>

    // 확인/미확인 처리 (simple)
    suspend fun patchNewsletterSimple(userNewsletterId: Long): NewsletterSimpleResult
}