package com.example.moblie_apps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class RecipesViewModel : ViewModel() {
    private val recipesFlow = MutableStateFlow<List<Recipe>>(emptyList())
    private val queryFlow = MutableStateFlow("")
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    val filteredRecipes: StateFlow<List<Recipe>> = queryFlow
        .debounce(300)
        .onStart {
            _loadingState.value = true
        }
        .onEach {
            delay(2000)
            _loadingState.value = true
        }
        .onEach { query ->
            if (query.isNotEmpty()) {
                _loadingState.value = true
            }
        }
        .map { query ->
            if (query.isEmpty()) {
                recipesFlow.value
            } else if (query.length >= 3) {
                delay(1000)
                recipesFlow.value.filter {
                    it.recipeName.contains(query, ignoreCase = true) ||
                            it.recipeInfo.contains(query, ignoreCase = true)
                }
            } else {
                emptyList()
            }
        }
        .onEach {
            _loadingState.value = false
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), recipesFlow.value)

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
