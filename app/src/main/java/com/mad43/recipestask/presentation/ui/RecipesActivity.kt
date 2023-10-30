package com.mad43.recipestask.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mad43.recipestask.R
import com.mad43.recipestask.databinding.ActivityMainBinding
import com.mad43.recipestask.presentation.state.ScreenState
import com.mad43.recipestask.presentation.viewmodel.RecipeViewModel
import com.mad43.recipestask.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        recipeAdapter = RecipeAdapter()
        checkNetwork()
    }


    private fun observeGettingAllRecipe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeViewModel.allRecipeResponse.collect {
                    when (it) {
                        is ScreenState.LOADING -> {
                            handleViews(AppConstants.LOADING)
                        }

                        is ScreenState.SUCCESS -> {
                            handleViews(AppConstants.SUCCESS)
                            val recipeList = it.data
                            recipeAdapter.setList(recipeList!!)
                            binding.recyclerRecipe.apply {
                                adapter = recipeAdapter
                            }
                        }

                        is ScreenState.ERROR -> {
                            handleViews(AppConstants.ERROR)
                        }
                    }
                }
            }
        }

    }

    private fun handleViews(state: String) {
        when (state) {
            AppConstants.LOADING -> {
                binding.progressBar.showProgress()
                binding.recyclerRecipe.visibilityGone()
                binding.imageViewConnection.visibilityGone()
                binding.textViewNoInternet.visibilityGone()
            }

            AppConstants.SUCCESS -> {
                binding.progressBar.hideProgress()
                binding.recyclerRecipe.visibilityVisible()
                binding.imageViewConnection.visibilityGone()
                binding.textViewNoInternet.visibilityGone()
            }

            AppConstants.ERROR -> {
                binding.progressBar.hideProgress()
                binding.recyclerRecipe.visibilityGone()
                binding.imageViewConnection.visibilityVisible()
                binding.textViewNoInternet.visibilityVisible()
                showToast(getString(R.string.noInternet))
            }

            AppConstants.NETWORK_AVAILABLE -> {
                binding.apply {
                    textViewNoConnection.visibilityGone()
                    recyclerRecipe.visibilityVisible()
                    imageViewConnection.visibilityGone()
                    textViewNoInternet.visibilityGone()
                }
            }

            else -> {
                binding.apply {
                    textViewNoConnection.visibilityVisible()
                    recyclerRecipe.visibilityGone()
                    imageViewConnection.visibilityVisible()
                    textViewNoInternet.visibilityVisible()
                }
            }
        }
    }

    private fun checkNetwork() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeViewModel.networkStatusFlow.collect { networkStatus ->
                    if (networkStatus == AppConstants.AVAILABLE) {
                        checkConnection(true)
                        recipeViewModel.getAllRecipe()
                        observeGettingAllRecipe()
                    } else {
                        checkConnection(false)
                    }
                }
            }
        }
    }

    private fun checkConnection(flag: Boolean) {
        if (flag) {
            handleViews(AppConstants.NETWORK_AVAILABLE)
        } else {
            handleViews(AppConstants.NETWORK_UNAVAILABLE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}