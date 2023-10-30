package com.mad43.recipestask.presentation.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mad43.recipestask.databinding.ItemRecipeBinding
import com.mad43.recipestask.domain.models.Recipe
import com.mad43.recipestask.utils.ImageDownloader
import com.mad43.recipestask.utils.setDifficulty
import java.util.concurrent.Executors

class RecipeAdapter() :
    RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    private var list: List<Recipe> = listOf()
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())

    fun setList(list: List<Recipe>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return RecipeHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.size
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = list[position]
        initData(recipe = recipe, binding = holder.binding)
    }

    private fun initData(recipe: Recipe, binding: ItemRecipeBinding) {
        binding.apply {
            textViewCalories.text = recipe.calories
            textViewDescription.text = recipe.description
            textViewDifficulties.text = setDifficulty(recipe.difficulty)
            textViewFats.text = recipe.fats
            textViewName.text = recipe.name
            textViewProteins.text = recipe.proteins
            downLoadImage(recipe.image, imageViewRecipe)
        }
    }

    private  fun downLoadImage(imagePath: String, imageView: ImageView) {
        myExecutor.execute {
            val imageBitMap = ImageDownloader().mLoad(imagePath)
            myHandler.post {
                imageView.setImageBitmap(imageBitMap)
            }
        }
    }

    inner class RecipeHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

}