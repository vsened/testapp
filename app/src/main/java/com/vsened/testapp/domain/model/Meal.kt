package com.vsened.testapp.domain.model

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val isFavorite: Boolean = false
)
