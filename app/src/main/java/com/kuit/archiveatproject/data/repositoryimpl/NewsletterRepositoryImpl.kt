package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.domain.entity.NewsletterSimpleResult
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import javax.inject.Inject

class NewsletterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsletterRepository {

    override suspend fun patchNewsletterRead(userNewsletterId: Long): Unit {
        apiService.patchNewsletterRead(userNewsletterId)
    }

    override suspend fun patchNewsletterSimple(userNewsletterId: Long): NewsletterSimpleResult {
        return apiService.patchNewsletterSimple(userNewsletterId).toEntity()
    }
}