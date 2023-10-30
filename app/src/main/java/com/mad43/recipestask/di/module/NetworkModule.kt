package com.mad43.recipestask.di.module

import com.mad43.recipestask.data.source.network.IRemoteDataSource
import com.mad43.recipestask.data.source.network.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideRemoteDataSource(): IRemoteDataSource {
        return RemoteDataSourceImpl()
    }
}

