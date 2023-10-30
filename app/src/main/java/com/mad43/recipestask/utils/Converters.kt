package com.mad43.recipestask.utils

import com.mad43.recipestask.domain.models.Recipe
import org.json.JSONArray

object Converters {

    fun parseJsonToRecipeList(jsonData: String): List<Recipe> {
        val recipeList = mutableListOf<Recipe>()
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val calories = jsonObject.getString("calories")
                val carbos = jsonObject.getString("carbos")
                val description = jsonObject.getString("description")
                val difficulty = jsonObject.getInt("difficulty")
                val fats = jsonObject.getString("fats")
                val headLine = jsonObject.getString("headline")
                val id = jsonObject.getString("id")
                val image = jsonObject.getString("image")
                val name = jsonObject.getString("name")
                val proteins = jsonObject.getString("proteins")
                val thumb = jsonObject.getString("thumb")
                val time = jsonObject.getString("time")

                val recipe = Recipe(
                    id = id,
                    image = image,
                    carbos = carbos,
                    headline = headLine,
                    time = time,
                    name = name,
                    proteins = proteins,
                    thumb = thumb,
                    difficulty = difficulty,
                    fats = fats,
                    description = description,
                    calories = calories
                )
                recipeList.add(recipe)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return recipeList
    }

}