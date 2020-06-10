package com.omarabbasi.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetail : AppCompatActivity() {

    private var recipeImage: ImageView? = null
    private var recipeName: TextView? = null
    private var recipeTags: TextView? = null
    private var recipeDirections: TextView? = null
    private lateinit var ingredientListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_detail)

        supportActionBar?.hide()
        recipeImage = findViewById(R.id.recipe_detail_image)
        recipeName = findViewById(R.id.recipe_detail_name)
        recipeTags = findViewById(R.id.recipe_detail_tags)
        recipeDirections = findViewById(R.id.recipe_detail_directions)
        ingredientListView = findViewById(R.id.recipe_detail_ingredients_view)

        val id = intent.getStringExtra("ID")
        getRecipeById(id)

    }

    private fun setRecipeInUI(recipe: FullRecipe) {

        Picasso.get().load(recipe.imageURL).into(recipeImage)
        recipeName?.text = recipe.name
        recipeTags?.text = recipe.tags.joinToString(" · ")
        recipeDirections?.text = recipe.instructions

        // set ingredients
        val sortedIngredients = recipe.ingredients.sortedBy { it.name }
        val listAdapter = IngredientListAdapter(sortedIngredients)
        ingredientListView.layoutManager = LinearLayoutManager(this)
        ingredientListView.adapter = listAdapter

    }

    private fun getRecipeById(id: String) {

        val mealService = APIFactory.mealDBAPI

        mealService.getRecipeById(id)
            .enqueue(object : Callback<JsonElement> {
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {

                        var json = response.body()?.asJsonObject!!
                        var recipesArr = json["meals"].asJsonArray!!
                        var recipeJSON = recipesArr[0].asJsonObject!!

                        // set in view
                        val id = recipeJSON["idMeal"].asString
                        val imgUrl = recipeJSON["strMealThumb"].asString
                        val recipeName = recipeJSON["strMeal"].asString
                        val recipeTags = recipeJSON["strTags"].asString.split(",")
                        val recipeArea = recipeJSON["strArea"].asString
                        val recipeCategory = recipeJSON["strCategory"].asString
                        val recipeDirections = recipeJSON["strInstructions"].asString
                        val websiteString = recipeJSON["strSource"].asString
                        val videoString = recipeJSON["strYoutube"].asString

                        val ingredients = arrayListOf<Ingredient>()

                        // sort and set ingredients
                        var i = 1
                        while (recipeJSON["strIngredient${i}"].asString.isNotEmpty() && i <= 20) {
                            val ingQuantity = recipeJSON["strMeasure${i}"].asString
                            var ingName = recipeJSON["strIngredient${i}"].asString
                            val ingredient = Ingredient(ingName, ingQuantity)

                            ingredients.add(ingredient)
                            i++
                        }

                        val recipe = FullRecipe(id, recipeName, recipeCategory, imgUrl, recipeArea,
                            recipeTags, videoString, websiteString, recipeDirections, ingredients)

                        setRecipeInUI(recipe)

                    }
                }

            })
    }

}
