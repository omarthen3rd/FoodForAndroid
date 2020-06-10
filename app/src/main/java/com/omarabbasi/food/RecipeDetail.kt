package com.omarabbasi.food

import android.content.Context
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

        var smallHeaderString = recipe.category
        if (recipe.tags.isNotEmpty()) smallHeaderString += " · ${recipe.tags.joinToString(" · ")}"

        Picasso.get().load(recipe.imageURL).into(recipeImage)
        recipeName?.text = recipe.name.split(' ').joinToString(" ") { it.capitalize() }
        recipeTags?.text = smallHeaderString
        recipeDirections?.text = recipe.instructions.replace("\r", System.lineSeparator())

        // set ingredients
        val sortedIngredients = recipe.ingredients.sortedBy { it.name }
        val listAdapter = IngredientListAdapter(sortedIngredients)
        ingredientListView.layoutManager = NoScrollLinearLayout(this)
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
                        val recipeArea = recipeJSON["strArea"].asString
                        val recipeTags = recipeJSON["strTags"]
                        val recipeCategory = recipeJSON["strCategory"].asString
                        val recipeDirections = recipeJSON["strInstructions"].asString
                        recipeDirections.replace("\r", "\n")

                        val websiteString = recipeJSON["strSource"]
                        val videoString = recipeJSON["strYoutube"]

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

                        // check tags
                        var tags = arrayOf<String>()
                        if (!recipeTags.isJsonNull) {
                            tags = recipeTags.asString.split(",").toTypedArray()
                        }

                        // check urls
                        var website = ""
                        if (!websiteString.isJsonNull) website = websiteString.asString

                        var video = ""
                        if (!videoString.isJsonNull) video = videoString.asString

                        val recipe = FullRecipe(id, recipeName, recipeCategory, imgUrl, recipeArea,
                            tags, video, website, recipeDirections, ingredients)

                        setRecipeInUI(recipe)

                    }
                }

            })
    }

}

class NoScrollLinearLayout(context: Context?) : LinearLayoutManager(context) {

    override fun canScrollVertically(): Boolean {
        return false
    }
}