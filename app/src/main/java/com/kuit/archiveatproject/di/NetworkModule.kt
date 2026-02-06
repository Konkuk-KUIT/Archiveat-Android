package com.kuit.archiveatproject.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuit.archiveatproject.BuildConfig
import com.kuit.archiveatproject.data.network.AuthInterceptor
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.service.AuthApiService
import com.kuit.archiveatproject.data.service.NewsletterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            // 일단 BODY로 두고, 나중에 debug/release 분기
            level = HttpLoggingInterceptor.Level.BODY
            /*
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            redactHeader("Authorization")
             */
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)    // Authorization 자동 추가
            .addInterceptor(loggingInterceptor) // 로그
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // local.properties에서 넣은 BASE_URL
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthApiService(
        retrofit: Retrofit
    ): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsletterApiService(retrofit: Retrofit): NewsletterApiService =
        retrofit.create(NewsletterApiService::class.java)

}