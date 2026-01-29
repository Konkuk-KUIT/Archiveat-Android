package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.data.util.requireSuccess
import com.kuit.archiveatproject.domain.entity.NewsletterSimpleResult
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import javax.inject.Inject

class NewsletterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsletterRepository {

    override suspend fun patchNewsletterRead(userNewsletterId: Long): Result<Unit> =
        runCatching {
            apiService.patchNewsletterRead(userNewsletterId).requireSuccess()
            Unit
        }

    override suspend fun patchNewsletterSimple(userNewsletterId: Long): NewsletterSimpleResult {
        val dto = apiService.patchNewsletterSimple(userNewsletterId).requireData()
        return dto.toEntity()
    }
}