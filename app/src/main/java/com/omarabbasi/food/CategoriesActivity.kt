package com.omarabbasi.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                        Log.d("MainActivity 1", response.body()?.toString())
                    }
                }

            })

    }


    
}
