package com.kuit.archiveatproject.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuit.archiveatproject.BuildConfig
import com.kuit.archiveatproject.data.network.AuthAuthenticator
import com.kuit.archiveatproject.data.network.AuthInterceptor
import com.kuit.archiveatproject.data.network.RefreshTokenCookieInterceptor
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.service.AuthApiService
import com.kuit.archiveatproject.data.service.NewsletterApiService
import com.kuit.archiveatproject.data.service.ReissueApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            redactHeader("Authorization")
        }
    }

    @Provides
    @Singleton
    @Named("NoAuthOkHttpClient")
    fun provideNoAuthOkHttpClient(
        refreshTokenCookieInterceptor: RefreshTokenCookieInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(refreshTokenCookieInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        refreshTokenCookieInterceptor: RefreshTokenCookieInterceptor,
        authAuthenticator: AuthAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)    // Authorization 자동 추가
            .addInterceptor(refreshTokenCookieInterceptor) // Set-Cookie(refreshToken) -> DataStore 저장
            .authenticator(authAuthenticator) // 401 -> reissue -> 원 요청 재시도
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
    @Named("NoAuthRetrofit")
    fun provideNoAuthRetrofit(
        @Named("NoAuthOkHttpClient") okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
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

    @Provides
    @Singleton
    fun provideReissueApiService(
        @Named("NoAuthRetrofit") retrofit: Retrofit
    ): ReissueApiService = retrofit.create(ReissueApiService::class.java)

}
