package com.example.moblie_apps

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import com.example.moblie_apps.CredentialsManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

data class Recipe(
    val imageId: Int,
    val recipeName: String,
    val recipeInfo: String
)

class MainActivity : AppCompatActivity() {

    private val viewModel: RecipesViewModel by viewModels()

    private val recipesRecyclerView
        get() = findViewById<RecyclerView>(R.id.recyclerview)

    private val recyclerView
        get() = findViewById<RecyclerView>(R.id.recyclerview)

    private val credentialsManager = CredentialsManager()

    private val progressIndicator
        get() = findViewById<CircularProgressIndicator>(R.id.progress_indicator)

    private val arrowBack
        get() = findViewById<ImageView>(R.id.arrow_back)

    private val text
        get() = findViewById<TextView>(R.id.japanese)

    private val logoutButton
        get() = findViewById<MaterialButton>(R.id.logout_button)

    private val isLoading = MutableStateFlow(false)

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recipes = initializeRecipes()
        viewModel.initializeRecipes(recipes)

        setupRecyclerView()
        setupSearchView()
        observeRecipes()

        updateUIBasedOnLoginState()

        logoutButton.setOnClickListener {
            logoutUser()
        }

        val loadingState: StateFlow<Boolean> = isLoading
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadingState.collect { loading ->
                    if (loading) {
                        progressIndicator.visibility = View.VISIBLE
                        recipesRecyclerView.visibility = View.GONE
                    } else {
                        progressIndicator.visibility = View.GONE
                        recipesRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initializeRecipes(): List<Recipe> {
        val imageId = intArrayOf(
            R.drawable.recipe_1, R.drawable.recipe_2, R.drawable.recipe_3,
            R.drawable.recipe_4, R.drawable.recipe_5, R.drawable.recipe_6, R.drawable.recipe_7
        )

        val recipeName = arrayOf(
            getString(R.string.recipe_black_karaage), getString(R.string.recipe_seafood_udon),
            getString(R.string.recipe_tonkotsu_ramen), getString(R.string.recipe_takoyaki),
            getString(R.string.recipe_tempura), getString(R.string.recipe_yakitori_shrimp),
            getString(R.string.recipe_onigiri_bento)
        )

        val recipeInfo = arrayOf(
            getString(R.string.info_black_karaage), getString(R.string.info_seafood_udon),
            getString(R.string.info_tonkotsu_ramen), getString(R.string.info_takoyaki),
            getString(R.string.info_tempura), getString(R.string.info_yakitori_shrimp),
            getString(R.string.info_onigiri_bento)
        )

        return List(7) { index ->
            Recipe(imageId[index], recipeName[index], recipeInfo[index])
        }
    }

    private fun setupRecyclerView() {
        recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        recipesRecyclerView.adapter = RecipeAdapter(emptyList())
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setQuery(newText.orEmpty())
                return true
            }
        })
    }

    private fun observeRecipes() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredRecipes.combine(viewModel.loadingState) { recipes, isLoading ->
                    if (isLoading) {
                        progressIndicator.visibility = View.VISIBLE
                        recipesRecyclerView.visibility = View.GONE
                    } else {
                        progressIndicator.visibility = View.GONE
                        recipesRecyclerView.visibility =
                            if (recipes.isEmpty()) View.GONE else View.VISIBLE
                        (recipesRecyclerView.adapter as RecipeAdapter).updateRecipes(recipes)
                    }
                }.collect { }

            }
        }
    }

    private fun logoutUser() {
        CredentialsManager.setLoggedIn(this, false)

        recyclerView.visibility = View.GONE
        text.visibility = View.GONE
        arrowBack.visibility = View.GONE
        logoutButton.visibility = MaterialButton.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, LoginFragment(credentialsManager))
            .commit()

        updateUIBasedOnLoginState()
    }

    private fun updateUIBasedOnLoginState() {
        if (CredentialsManager.isLoggedIn(this)) {
            recyclerView.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
            arrowBack.visibility = View.VISIBLE
            logoutButton.visibility = MaterialButton.VISIBLE
            loadRecyclerViewData()
        } else {
            recyclerView.visibility = View.GONE
            text.visibility = View.GONE
            arrowBack.visibility = View.GONE
            logoutButton.visibility = MaterialButton.GONE
        }
    }

    private fun loadRecyclerViewData() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

class RecipeAdapter(private var recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage: ImageView = itemView.findViewById(R.id.recipe_image)
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        val recipeInfo: TextView = itemView.findViewById(R.id.recipe_description)
        val shareButton: Button = itemView.findViewById(R.id.share_button)
        val likeButton: Button = itemView.findViewById(R.id.like_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeName.text = recipe.recipeName
        holder.recipeInfo.text = recipe.recipeInfo
        holder.recipeImage.setImageResource(recipe.imageId)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Clicked: ${recipe.recipeName}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.shareButton.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Share with: ${recipe.recipeName}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.likeButton.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Added to Favourites: ${recipe.recipeName}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}


