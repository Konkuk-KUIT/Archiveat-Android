package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.Home

interface HomeRepository {
    suspend fun getHome(): Home
}