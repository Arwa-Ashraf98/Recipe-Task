package com.mad43.recipestask.domain.repo

import com.mad43.recipestask.domain.models.Recipe
import com.mad43.recipestask.domain.resources.ResourceResult
import kotlinx.coroutines.flow.Flow

interface IAllRecipeRepo {
    fun getAllRecipe() : Flow<ResourceResult<List<Recipe>>>
}