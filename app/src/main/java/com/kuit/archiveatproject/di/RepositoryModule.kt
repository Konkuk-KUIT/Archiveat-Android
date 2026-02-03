package com.kuit.archiveatproject.di

import com.kuit.archiveatproject.data.repositoryimpl.AuthRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.CollectionRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.ExploreRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.ExploreTopicNewslettersRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.HomeRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.InboxClassificationRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.InboxRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.ReportRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.TokenRepositoryImpl
import com.kuit.archiveatproject.data.repositoryimpl.UserMetadataRepositoryImpl
import com.kuit.archiveatproject.domain.repository.AuthRepository
import com.kuit.archiveatproject.domain.repository.CollectionRepository
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import com.kuit.archiveatproject.domain.repository.ExploreTopicNewslettersRepository
import com.kuit.archiveatproject.domain.repository.HomeRepository
import com.kuit.archiveatproject.domain.repository.InboxClassificationRepository
import com.kuit.archiveatproject.domain.repository.InboxRepository
import com.kuit.archiveatproject.domain.repository.ReportRepository
import com.kuit.archiveatproject.domain.repository.TokenRepository
import com.kuit.archiveatproject.domain.repository.UserMetadataRepository
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
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        impl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindExploreRepository(
        impl: ExploreRepositoryImpl
    ): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindInboxRepository(
        impl: InboxRepositoryImpl
    ): InboxRepository

    @Binds
    @Singleton
    abstract fun bindExploreTopicNewslettersRepository(
        impl: ExploreTopicNewslettersRepositoryImpl
    ): ExploreTopicNewslettersRepository

    @Binds
    @Singleton
    abstract fun bindInboxClassificationRepository(
        impl: InboxClassificationRepositoryImpl
    ): InboxClassificationRepository

    @Binds
    @Singleton
    abstract fun bindReportRepository(
        impl: ReportRepositoryImpl
    ): ReportRepository

    @Binds
    @Singleton
    abstract fun bindUserMetadataRepository(
        impl: UserMetadataRepositoryImpl
    ): UserMetadataRepository

    @Binds
    @Singleton
    abstract fun bindCollectionRepository(
        impl: CollectionRepositoryImpl
    ): CollectionRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository
}