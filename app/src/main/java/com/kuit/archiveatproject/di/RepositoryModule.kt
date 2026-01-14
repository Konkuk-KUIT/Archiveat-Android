package com.kuit.archiveatproject.di

import com.kuit.archiveatproject.data.repositoryimpl.ExploreRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.TokenRepositoryImpl
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import com.kuit.archiveatproject.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindExploreRepository(
        impl: ExploreRepositoryImpl
    ): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository
}