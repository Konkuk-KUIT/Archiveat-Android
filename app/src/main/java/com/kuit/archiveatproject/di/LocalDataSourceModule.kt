package com.kuit.archiveatproject.di

import com.kuit.archiveatproject.data.local.TokenLocalDataSource
import com.kuit.archiveatproject.data.local.TokenLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTokenLocalDataSource(
        impl: TokenLocalDataSourceImpl
    ): TokenLocalDataSource
}