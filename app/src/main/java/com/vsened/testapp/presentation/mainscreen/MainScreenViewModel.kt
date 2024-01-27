package com.vsened.testapp.presentation.mainscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsened.testapp.data.repository.RepositoryImpl
import com.vsened.testapp.domain.model.Meal
import com.vsened.testapp.domain.repository.Repository
import com.vsened.testapp.domain.usecases.CheckMealInFavoriteUseCase
import com.vsened.testapp.domain.usecases.GetMealsUseCase
import com.vsened.testapp.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val checkFavoritesMealsUseCase: CheckMealInFavoriteUseCase,
    private val getMealsUseCase: GetMealsUseCase
): ViewModel() {

    var state by mutableStateOf(MealsListState())

    private val _sharedItems = MutableStateFlow(value = emptyList<Meal>())
    val sharedItems = _sharedItems.asSharedFlow()

    init {
        getMeals()
    }
    private fun getMeals() {
        viewModelScope.launch {
            getMealsUseCase().collect {result ->
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
                            val newMeal = ArrayList<Meal>()
                            meals.forEach {
                                newMeal.add(it.copy(
                                    isFavorite = checkFavoritesMealsUseCase(it.idMeal)
                                ))
                            }
                            state = state.copy(
                                meals = newMeal
                            )
                        }
                    }
                }
            }
        }
    }
}