package com.omarabbasi.food

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIFactory {

    private val mealDBClient = OkHttpClient().newBuilder().build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(mealDBClient)
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val mealDBAPI: MealsEndpoint = retrofit().create(MealsEndpoint::class.java)

}