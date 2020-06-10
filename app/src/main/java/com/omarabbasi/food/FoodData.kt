package com.omarabbasi.food

import com.google.gson.JsonElement
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
    val id: String,
    val name: String,
    val category: String,
    val imageURL: String,
    val area: String,
    val tags: Array<String>,
    val youtube: String,
    val website: String,
    val instructions: String,
    val ingredients: List<Ingredient>
)

data class Ingredient(val name: String, val quantity: String)

// endpoints here
interface MealsEndpoint {

    // AllCategoriesResponse / Category data
    @GET("categories.php")
    fun getCategories(): Call<AllCategoriesResponse>

    @GET("filter.php")
    fun getRecipesByFilter(@Query("c") filter: String): Call<CategoryRecipes>

    @GET("lookup.php")
    fun getRecipeById(@Query("i") id: String): Call<JsonElement>

}
