package com.omarabbasi.food

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//
// Category
data class AllCategoriesResponse(
    val categories: List<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String
)


// BasicRecipe
data class CategoryRecipes(
    val meals: List<BasicRecipe>
)

data class BasicRecipe(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class FullRecipes(
    val meals: List<FullRecipe>
)

data class FullRecipe (
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strMealThumb: String,
    val strArea: String,
    val strTags: String,
    val strYoutube: String,
    val strSource: String
)

// endpoints here
interface MealsEndpoint {

    // AllCategoriesResponse / Category data
    @GET("categories.php")
    fun getCategories(): Call<AllCategoriesResponse>

    @GET("filter.php")
    fun getRecipesByFilter(@Query("c") filter: String): Call<CategoryRecipes>

    @GET("lookup.php")
    fun getRecipeById(@Query("i") id: String): Call<FullRecipes>

}
