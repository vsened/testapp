package com.vsened.testapp.presentation.mainscreen

import com.vsened.testapp.domain.model.Meal

data class MealsListState (
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String = ""
)