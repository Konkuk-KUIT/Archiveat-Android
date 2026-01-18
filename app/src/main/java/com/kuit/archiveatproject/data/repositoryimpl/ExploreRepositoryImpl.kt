package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toDomain
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.domain.model.Explore
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ExploreRepository {

    override suspend fun getExplore(): Explore {
        return apiService.getExplore().toDomain()
    }
}