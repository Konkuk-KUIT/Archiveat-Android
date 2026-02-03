package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.dto.request.newsletter.NewsletterSaveRequestDto
import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.NewsletterApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.data.util.requireSuccess
import com.kuit.archiveatproject.domain.entity.NewsletterDetail
import com.kuit.archiveatproject.domain.entity.NewsletterSaveResult
import com.kuit.archiveatproject.domain.entity.NewsletterSimple
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsletterRepositoryImpl @Inject constructor(
    private val newsletterApiService: NewsletterApiService
) : NewsletterRepository {
    override suspend fun getNewsletterDetail(userNewsletterId: Long): NewsletterDetail {
        val res = newsletterApiService.getNewsletterDetail(userNewsletterId)
        return res.requireData().toEntity()
    }

    override suspend fun getNewsletterSimple(userNewsletterId: Long): NewsletterSimple {
        val res = newsletterApiService.getNewsletterSimple(userNewsletterId)
        return res.requireData().toEntity()
    }

    override suspend fun saveNewsletter(contentUrl: String, memo: String): NewsletterSaveResult {
        val res = newsletterApiService.saveNewsletter(
            NewsletterSaveRequestDto(contentUrl = contentUrl, memo = memo)
        )
        return res.requireData().toEntity()
    }

    override suspend fun deleteNewsletter(userNewsletterId: Long): Long {
        val res = newsletterApiService.deleteNewsletter(userNewsletterId)
        return res.requireData().userNewsletterId
    }

    override suspend fun patchNewsletterRead(userNewsletterId: Long) {
        val res = newsletterApiService.patchNewsletterRead(userNewsletterId)
        // data 없음
        res.requireSuccess()
    }
}