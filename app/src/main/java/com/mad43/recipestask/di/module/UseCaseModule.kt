package com.mad43.recipestask.di.module

import com.mad43.recipestask.domain.repo.IAllRecipeRepo
import com.mad43.recipestask.domain.usecase.GetAllRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideAllRecipeUseCase(repo: IAllRecipeRepo): GetAllRecipeUseCase =
        GetAllRecipeUseCase(repo)
}