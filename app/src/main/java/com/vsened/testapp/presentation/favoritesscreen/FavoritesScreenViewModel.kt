package com.vsened.testapp.presentation.favoritesscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsened.testapp.domain.usecases.CheckMealInFavoriteUseCase
import com.vsened.testapp.domain.usecases.GetFavoritesMealsUseCase
import com.vsened.testapp.domain.usecases.GetMealsUseCase
import com.vsened.testapp.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val getFavoritesMealsUseCase: GetFavoritesMealsUseCase
): ViewModel() {

    var state by mutableStateOf(FavoritesScreenState())

    init {
        getFavoritesMeals()
    }
    private fun getFavoritesMeals() {
        viewModelScope.launch {
            getFavoritesMealsUseCase().collect {result ->
                when(result) {
                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { meals ->
                            state = state.copy(
                                meals = meals
                            )
                        }
                    }
                }
            }
        }
    }
}