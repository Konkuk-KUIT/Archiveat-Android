package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toDomain
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.model.Home
import com.kuit.archiveatproject.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {

    override suspend fun getHome(): Home {
        val dto = apiService.getHome().requireData()
        // 필요하면 여기서 isSuccess 체크해서 throw
        return dto.toDomain()
    }
}