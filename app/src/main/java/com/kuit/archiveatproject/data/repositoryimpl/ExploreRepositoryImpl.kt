package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.entity.Explore
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ExploreRepository {

    override suspend fun getExplore(): Explore {
        val dto = apiService.getExplore().requireData()
        return dto.toEntity()
    }
}