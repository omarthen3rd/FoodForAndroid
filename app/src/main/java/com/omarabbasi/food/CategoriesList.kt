package com.omarabbasi.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ListView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesList : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    private lateinit var categories: List<Category>
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_list)

        listView = findViewById(R.id.categories_list_view)
        getCategories()

    }

    private fun onClickForCategory(category: Category) {

        val detailIntent = Intent(this, RecipeList::class.java).apply {
            putExtra("NAME", category.strCategory)
        }

        startActivity(detailIntent)

    }

    private fun getCategories() {

        val mealService = APIFactory.mealDBAPI

        mealService.getCategories()
            .enqueue(object : Callback<AllCategoriesResponse> {
                override fun onFailure(call: Call<AllCategoriesResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<AllCategoriesResponse>, response: Response<AllCategoriesResponse>) {
                    if (response.isSuccessful) {
                        val categoriesList = response.body() as AllCategoriesResponse
                        val sortedCategories = categoriesList.categories.sortedBy { it.strCategory }
                        categories = sortedCategories
                        val listAdapter = CategoryListAdapter(sortedCategories) {
                                category: Category -> onClickForCategory(category)
                        }
                        listView.layoutManager = LinearLayoutManager(context)
                        listView.adapter = listAdapter

                    }
                }

            })

    }



}
