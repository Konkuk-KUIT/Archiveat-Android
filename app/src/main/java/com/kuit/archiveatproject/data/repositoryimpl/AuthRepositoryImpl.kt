package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.dto.request.auth.CheckEmailRequestDto
import com.kuit.archiveatproject.data.dto.request.auth.LoginRequestDto
import com.kuit.archiveatproject.data.dto.request.auth.SignupRequestDto
import com.kuit.archiveatproject.data.service.AuthApiService
import com.kuit.archiveatproject.data.util.requireData
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
        val token = res.requireData().accessToken
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
        val token = res.requireData().accessToken
        tokenRepository.saveAccessToken(token)
        return token
    }

    override suspend fun logout() {
        try {
            // 서버에 로그아웃 요청 (실패할 수도 있음)
            authApiService.logout()
        } finally {
            // 로컬 로그아웃은 무조건 보장
            tokenRepository.clearAccessToken()
        }
    }

    override suspend fun checkEmail(email: String): Boolean {
        val res = authApiService.checkEmail(
            CheckEmailRequestDto(email = email)
        )
        return res.requireData()
    }

    override suspend fun reissue(): String {
        // 이번 MVP(재시작 로그아웃)에서는 보통 안 씀
        val res = authApiService.reissue()
        val token = res.requireData().accessToken
        tokenRepository.saveAccessToken(token)
        return token
    }
}