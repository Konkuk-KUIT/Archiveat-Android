package com.kuit.archiveatproject

import com.kuit.archiveatproject.di.ApplicationScope
import com.kuit.archiveatproject.domain.repository.TokenRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class Application: android.app.Application(){
    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    @ApplicationScope
    lateinit var appScope: CoroutineScope

    override fun onCreate(){
        super.onCreate()
        instance = this

        // "앱 재시작(프로세스 새로 시작)"하면 무조건 로그아웃
        appScope.launch {
            tokenRepository.clearAccessToken()
        }
    }

    companion object {
        lateinit var instance: Application
            private set
    }
}