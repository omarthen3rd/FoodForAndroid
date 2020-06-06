package com.omarabbasi.food

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

// data objects here
data class AllCategoriesResponse(
    val categories: List<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String
)

// endpoints here
interface MealsEndpoint {

    // AllCategoriesResponse / Category data
    @GET("categories.php")
    fun getCategories(): Call<AllCategoriesResponse>

    // TODO:
    // other endpoints (Query, Details...)

}

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}