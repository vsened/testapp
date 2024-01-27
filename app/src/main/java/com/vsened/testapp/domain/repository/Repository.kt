package com.vsened.testapp.domain.repository

import com.vsened.testapp.domain.model.DetailMeal
import com.vsened.testapp.domain.model.Meal
import com.vsened.testapp.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun addFavoriteMeal(meal: DetailMeal)

    suspend fun removeFavoriteMeal(id: String)
    suspend fun getMeals(): Flow<Resource<List<Meal>>>
    suspend fun getFavoriteMeals(): Flow<Resource<List<Meal>>>
    fun getRemoveMealById(id: String): Flow<Resource<DetailMeal>>
    fun getLocalMealById(id: String): Flow<Resource<DetailMeal>>
    fun getFavoriteMealById(id: String): Flow<Resource<DetailMeal>>
    suspend fun isFavoriteMeal(id: String): Boolean
}