package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.Explore

interface ExploreRepository {
    suspend fun getExplore(): Explore
}