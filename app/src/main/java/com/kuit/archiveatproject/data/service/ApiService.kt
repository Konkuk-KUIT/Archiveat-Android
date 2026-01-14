package com.kuit.archiveatproject.data.service

import com.kuit.archiveatproject.data.dto.response.ExploreResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("/explore")
    suspend fun getExplore(): ExploreResponseDto
}