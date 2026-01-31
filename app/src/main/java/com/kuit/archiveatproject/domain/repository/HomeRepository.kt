package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.model.Home

interface HomeRepository {
    suspend fun getHome(): Home
}