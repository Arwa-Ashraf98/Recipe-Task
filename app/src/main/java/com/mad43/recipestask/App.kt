package com.mad43.recipestask

import android.app.Application
import com.mad43.recipestask.utils.network.NetworkConnectivityObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkConnectivityObserver.initNetworkConnectivityObserver(this)
    }

}