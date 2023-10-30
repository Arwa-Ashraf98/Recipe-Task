package com.mad43.recipestask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mad43.recipestask.domain.models.Recipe
import com.mad43.recipestask.domain.resources.ResourceResult
import com.mad43.recipestask.domain.usecase.GetAllRecipeUseCase
import com.mad43.recipestask.presentation.state.ScreenState
import com.mad43.recipestask.utils.network.NetworkConnectivityObserver
import com.mad43.recipestask.utils.network.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val allRecipeUseCase: GetAllRecipeUseCase) :
    ViewModel() {
    private val _allRecipeResponse =
        MutableStateFlow<ScreenState<List<Recipe>>>(ScreenState.LOADING())
    val allRecipeResponse = _allRecipeResponse.asStateFlow()
    private val _networkStatusFlow = MutableStateFlow("")
    val networkStatusFlow = _networkStatusFlow.asStateFlow()

    init {
        provideNetworkStatus()
    }

    private fun provideNetworkStatus() {

        NetworkConnectivityObserver.observeNetworkConnection().onEach { networkStatus ->
            when (networkStatus) {
                NetworkStatus.Available -> {
                    _networkStatusFlow.value = NetworkStatus.Available.toString()
                }
                NetworkStatus.Unavailable -> {
                    _networkStatusFlow.value = NetworkStatus.Unavailable.toString()
                }
                NetworkStatus.Losing -> {
                    _networkStatusFlow.value = NetworkStatus.Losing.toString()
                }
                else -> {
                    _networkStatusFlow.value = NetworkStatus.Lost.toString()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllRecipe() {
        viewModelScope.launch(Dispatchers.Main) {
            val flow = allRecipeUseCase()
            flow.collect { resourceResult ->
                when (resourceResult) {
                    is ResourceResult.Success -> {
                        val recipeList = resourceResult.data
                        _allRecipeResponse.emit(ScreenState.SUCCESS(recipeList))
                    }

                    is ResourceResult.Error -> {
                        val error = resourceResult.error?.localizedMessage
                        _allRecipeResponse.emit(ScreenState.ERROR(error))
                    }
                }
            }
        }
    }
}