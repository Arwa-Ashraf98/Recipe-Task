package com.mad43.recipestask.domain.usecase

import com.mad43.recipestask.domain.models.Recipe
import com.mad43.recipestask.domain.resources.ResourceResult
import com.mad43.recipestask.domain.repo.IAllRecipeRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecipeUseCase @Inject constructor(private val repo : IAllRecipeRepo) {
    operator fun invoke() : Flow<ResourceResult<List<Recipe>>> {
        return repo.getAllRecipe()
    }
}