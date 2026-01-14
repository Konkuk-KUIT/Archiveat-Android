package com.kuit.archiveatproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

// 이 파일 안에 DI에 쓰일 의존성 제공하는 함수가 있다 선언
@Module
// 앱 전체에 싱글톤 스코프로 사용될 의존성 제공 선언
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides // Hilt가 이 함수 호출해서 HttpLoggingInterceptor 인스턴스 만들어 DI 그래프에 넣음
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
}