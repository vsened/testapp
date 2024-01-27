package com.vsened.testapp.presentation.favoritesscreen

import com.vsened.testapp.domain.model.Meal

data class FavoritesScreenState (
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String = ""
)