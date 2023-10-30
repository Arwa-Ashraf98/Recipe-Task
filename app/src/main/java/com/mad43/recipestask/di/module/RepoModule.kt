package com.mad43.recipestask.di.module

import com.mad43.recipestask.data.repo.AllRecipeRepoImpl
import com.mad43.recipestask.data.source.network.IRemoteDataSource
import com.mad43.recipestask.domain.repo.IAllRecipeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideAllRecipeRepo(remoteDataSourceImpl: IRemoteDataSource) : IAllRecipeRepo {
        return AllRecipeRepoImpl(remoteDataSource = remoteDataSourceImpl)
    }

}