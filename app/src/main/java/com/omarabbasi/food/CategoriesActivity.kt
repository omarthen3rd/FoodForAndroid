package com.omarabbasi.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.categories_list)
        getCategories()

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
                        val listAdapter = CategoryAdapter(context, sortedCategories)
                        listView.adapter = listAdapter

                    }
                }

            })

    }


    
}
