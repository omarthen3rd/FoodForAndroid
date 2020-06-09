package com.omarabbasi.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetail : AppCompatActivity() {

    var recipeImage: ImageView? = null
    var recipeName: TextView? = null
    var recipeTags: TextView? = null
    var recipeDiections: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_detail)

        supportActionBar?.hide()
        recipeImage = findViewById(R.id.recipe_detail_image)
        recipeName = findViewById(R.id.recipe_detail_name)
        recipeTags = findViewById(R.id.recipe_detail_tags)
        recipeDiections = findViewById(R.id.recipe_detail_directions)

        val id = intent.getStringExtra("ID")
        getRecipeById(id)

    }

    private fun getRecipeById(id: String) {

        val mealService = APIFactory.mealDBAPI

        mealService.getRecipeById(id)
            .enqueue(object : Callback<FullRecipes> {
                override fun onFailure(call: Call<FullRecipes>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<FullRecipes>, response: Response<FullRecipes>) {
                    if (response.isSuccessful) {
                        val recipesList = response.body() as FullRecipes
                        val recipe = recipesList.meals[0]

                        // set in view
                        Picasso.get().load(recipe.strMealThumb).into(recipeImage)
                        recipeName?.text = recipe.strMeal.capitalize()
                        recipeTags?.text = (recipe.strArea + " · " +
                                recipe.strTags.replace(",", " · "))
                        recipeDiections?.text = recipe.strInstructions

                        // sort and set ingredients

                    }
                }

            })
    }

}
