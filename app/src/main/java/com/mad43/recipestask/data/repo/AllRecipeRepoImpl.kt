package com.mad43.recipestask.data.repo

import com.mad43.recipestask.data.source.network.IRemoteDataSource
import com.mad43.recipestask.domain.models.Recipe
import com.mad43.recipestask.domain.repo.IAllRecipeRepo
import com.mad43.recipestask.domain.resources.ResourceResult
import com.mad43.recipestask.utils.Converters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllRecipeRepoImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val remoteDataSource: IRemoteDataSource
) : IAllRecipeRepo {

    private lateinit var recipeList: List<Recipe>

    override fun getAllRecipe(): Flow<ResourceResult<List<Recipe>>> {
        return flow<ResourceResult<List<Recipe>>> {
            val recipesString = remoteDataSource.getAllRecipe()
            convertRecipeStringIntRecipeList(recipesString)
            emit(ResourceResult.Success(recipeList))
        }.catch {
            emit(ResourceResult.Error(it))
        }.flowOn(defaultDispatcher)
    }

    private suspend fun convertRecipeStringIntRecipeList(recipeString: String): List<Recipe> {
        withContext(Dispatchers.Main) {
            recipeList = Converters.parseJsonToRecipeList(recipeString)
        }
        return recipeList
    }
}