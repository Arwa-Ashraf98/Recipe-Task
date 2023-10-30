package com.mad43.recipestask.utils.network

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityObserverInterface {
    fun observeNetworkConnection(): Flow<NetworkStatus>
}
