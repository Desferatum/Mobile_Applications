package com.example.moblie_apps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecipesViewModel : ViewModel() {
    private val recipesFlow = MutableStateFlow<List<Recipe>>(emptyList())
    private val queryFlow = MutableStateFlow("")

    val filteredRecipes: StateFlow<List<Recipe>> = queryFlow
        .map { query ->
            if (query.length >= 3) {
                recipesFlow.value.filter {
                    it.recipeName.contains(query, ignoreCase = true) ||
                            it.recipeInfo.contains(query, ignoreCase = true)
                }
            } else {
                recipesFlow.value
            }
        }
        .stateIn(
            viewModelScope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    fun setQuery(query: String) {
        queryFlow.value = query
    }

    fun initializeRecipes(recipes: List<Recipe>) {
        recipesFlow.value = recipes
    }

    companion object {
        val MOCKED_RECIPES = listOf(
            Recipe(R.drawable.recipe_1, "Black Karaage", "Delicious crispy chicken"),
            Recipe(R.drawable.recipe_2, "Seafood Udon", "Rich noodle soup"),
            Recipe(R.drawable.recipe_3, "Tonkotsu Ramen", "Savory pork broth"),
            Recipe(R.drawable.recipe_4, "Takoyaki", "Crispy octopus balls"),
            Recipe(R.drawable.recipe_5, "Tempura", "Crispy fried vegetables"),
            Recipe(R.drawable.recipe_6, "Yakitori Shrimp", "Grilled skewers"),
            Recipe(R.drawable.recipe_7, "Onigiri Bento", "Rice balls with fillings")
        )
    }
}
