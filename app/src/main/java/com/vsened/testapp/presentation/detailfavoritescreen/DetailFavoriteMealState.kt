package com.vsened.testapp.presentation.detailfavoritescreen

import com.vsened.testapp.domain.model.DetailMeal

data class DetailFavoriteMealState(
    val isLoading: Boolean = false,
    val meal: DetailMeal? = null,
    val error: String = "",
    val isFavorite: Boolean = true
)

