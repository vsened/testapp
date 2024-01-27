package com.vsened.testapp.data.remove

import com.vsened.testapp.data.remove.model.DetailMealDto
import com.vsened.testapp.data.remove.model.MealDto
import com.vsened.testapp.data.remove.model.MealsDto
import com.vsened.testapp.data.remove.model.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/json/v1/1/filter.php?c=Beef")
    suspend fun getMeals(): MealsDto

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealById(
        @Query(QUERY_PARAM_MEAL) id: Int
    ):RecipeDto

    companion object {
        const val BASE_URL = "https://www.themealdb.com/"
        private const val QUERY_PARAM_MEAL = "i"
    }
}