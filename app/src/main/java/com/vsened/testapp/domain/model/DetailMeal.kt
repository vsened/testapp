package com.vsened.testapp.domain.model


data class DetailMeal(
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strNote: String? = null
)