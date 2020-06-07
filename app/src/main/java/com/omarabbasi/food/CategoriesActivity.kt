package com.omarabbasi.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var categories: List<Category>
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_list)

        listView = findViewById(R.id.categories_list_view)
        getCategories()

        listView.setOnItemClickListener { _, _, position, _ ->
            // 1
            val selectedCategory = categories[position]

            // 2
            val detailIntent = Intent(this, CategoryDetailActivity::class.java).apply {
                putExtra("NAME", selectedCategory.strCategory)
            }

            // 3
            startActivity(detailIntent)
        }


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
                        val listAdapter = CategoryAdapter(context, sortedCategories)
                        listView.adapter = listAdapter

                    }
                }

            })

    }



}
