package com.kuit.archiveatproject

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: android.app.Application(){
    override fun onCreate(){
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application
            private set
    }
}