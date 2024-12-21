package com.example.moblie_apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Recipe(
    val imageId: Int,
    val recipeName: String,
    val recipeInfo: String
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageId = intArrayOf(
            R.drawable.recipe_1,
            R.drawable.recipe_2,
            R.drawable.recipe_3,
            R.drawable.recipe_4,
            R.drawable.recipe_5,
            R.drawable.recipe_6,
            R.drawable.recipe_7
        )

        val recipeName = arrayOf(
            getString(R.string.recipe_black_karaage),
            getString(R.string.recipe_seafood_udon),
            getString(R.string.recipe_tonkotsu_ramen),
            getString(R.string.recipe_takoyaki),
            getString(R.string.recipe_tempura),
            getString(R.string.recipe_yakitori_shrimp),
            getString(R.string.recipe_onigiri_bento)
        )

        val recipeInfo = arrayOf(
            getString(R.string.info_black_karaage),
            getString(R.string.info_seafood_udon),
            getString(R.string.info_tonkotsu_ramen),
            getString(R.string.info_takoyaki),
            getString(R.string.info_tempura),
            getString(R.string.info_yakitori_shrimp),
            getString(R.string.info_onigiri_bento)
        )

        val recipes = List(7) { index ->
            Recipe(
                imageId[index],
                recipeName[index],
                recipeInfo[index]
            )
        }

        val recipesRecyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        recipesRecyclerView.adapter = RecipeAdapter(recipes)
    }

    class RecipeAdapter(private val recipes: List<Recipe>) :
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
    }
}
