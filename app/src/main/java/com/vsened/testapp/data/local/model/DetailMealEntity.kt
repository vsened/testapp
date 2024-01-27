package com.vsened.testapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailMealEntity(
    @PrimaryKey val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strNote: String? = null
)