package com.omarabbasi.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeList: AppCompatActivity() {

    private lateinit var listView: RecyclerView
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_list)

        listView = findViewById(R.id.recipes_list_view)
        val filter = intent.getStringExtra("NAME")

        getFilteredRecipes(filter)
    }

    private fun getFilteredRecipes(filter: String) {

        val mealService = APIFactory.mealDBAPI

        mealService.getRecipesByFilter(filter)
            .enqueue(object : Callback<CategoryRecipes> {
                override fun onFailure(call: Call<CategoryRecipes>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<CategoryRecipes>, response: Response<CategoryRecipes>) {
                    if (response.isSuccessful) {
                        val recipesList = response.body() as CategoryRecipes
                        val sortedRecipes = recipesList.meals.sortedBy { it.strMeal }
                        val listAdapter = RecipeListAdapter(sortedRecipes)
                        listView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        listView.adapter = listAdapter
                    }
                }

            })

    }
}
