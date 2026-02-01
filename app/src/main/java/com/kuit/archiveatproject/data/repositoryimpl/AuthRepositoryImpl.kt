package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.dto.request.auth.CheckEmailRequestDto
import com.kuit.archiveatproject.data.dto.request.auth.LoginRequestDto
import com.kuit.archiveatproject.data.dto.request.auth.SignupRequestDto
import com.kuit.archiveatproject.data.service.AuthApiService
import com.kuit.archiveatproject.domain.repository.AuthRepository
import com.kuit.archiveatproject.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenRepository: TokenRepository,
) : AuthRepository {

    override suspend fun signup(email: String, password: String, nickname: String): String {
        val res = authApiService.signup(
            SignupRequestDto(
                email = email,
                password = password,
                nickname = nickname
            )
        )
        val token = res.data.accessToken
        tokenRepository.saveAccessToken(token)
        return token
    }

    override suspend fun login(email: String, password: String): String {
        val res = authApiService.login(
            LoginRequestDto(
                email = email,
                password = password
            )
        )
        val token = res.data.accessToken
        tokenRepository.saveAccessToken(token)
        return token
    }

    override suspend fun logout() {
        // 서버에도 로그아웃 요청 (refreshToken 무효화 등)
        authApiService.logout()

        // 로컬 accessToken 삭제
        tokenRepository.clearAccessToken()
    }

    override suspend fun checkEmail(email: String): Boolean {
        val res = authApiService.checkEmail(
            CheckEmailRequestDto(email = email)
        )
        return res.data
    }

    override suspend fun reissue(): String {
        // 이번 MVP(재시작 로그아웃)에서는 보통 안 씀
        val res = authApiService.reissue()
        val token = res.data.accessToken
        tokenRepository.saveAccessToken(token)
        return token
    }
}