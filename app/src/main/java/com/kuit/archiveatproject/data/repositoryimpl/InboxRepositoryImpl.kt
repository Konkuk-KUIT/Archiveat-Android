package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.entity.Inbox
import com.kuit.archiveatproject.domain.repository.InboxRepository
import javax.inject.Inject

class InboxRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : InboxRepository {

    override suspend fun getInbox(): Inbox {
        val dto = apiService.getExploreInbox().requireData()
        return dto.toEntity()
    }
}