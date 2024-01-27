package com.vsened.testapp.presentation.detailscreen

import com.vsened.testapp.domain.model.DetailMeal

data class DetailMealState(
    val isLoading: Boolean = false,
    val meal: DetailMeal? = null,
    val error: String = "",
    val isFavorite: Boolean = false
)

