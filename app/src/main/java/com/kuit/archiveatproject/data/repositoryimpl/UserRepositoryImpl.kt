package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getNickname(): String {
        return apiService.getUserNickname()
            .requireData()
            .nickname
    }
}
