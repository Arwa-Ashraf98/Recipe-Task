package com.mad43.recipestask.data.source.network

interface IRemoteDataSource {
    suspend fun getAllRecipe() : String
}