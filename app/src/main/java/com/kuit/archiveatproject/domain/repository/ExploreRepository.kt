package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.model.Explore

interface ExploreRepository {
    suspend fun getExplore(): Explore
}