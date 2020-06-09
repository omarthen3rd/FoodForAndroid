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
    val strSource: String,
    val strInstructions: String,
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strIngredient16: String,
    val strIngredient17: String,
    val strIngredient18: String,
    val strIngredient19: String,
    val strIngredient20: String,
    val strMeasure1: String,
    val strMeasure2: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: String,
    val strMeasure6: String,
    val strMeasure7: String,
    val strMeasure8: String,
    val strMeasure9: String,
    val strMeasure10: String,
    val strMeasure11: String,
    val strMeasure12: String,
    val strMeasure13: String,
    val strMeasure14: String,
    val strMeasure15: String,
    val strMeasure16: String,
    val strMeasure17: String,
    val strMeasure18: String,
    val strMeasure19: String,
    val strMeasure20: String
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
