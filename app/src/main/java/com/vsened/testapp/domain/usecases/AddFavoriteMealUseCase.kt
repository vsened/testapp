package com.vsened.testapp.domain.usecases

import com.vsened.testapp.domain.model.DetailMeal
import com.vsened.testapp.domain.repository.Repository
import javax.inject.Inject

class AddFavoriteMealUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(meal: DetailMeal) = repository.addFavoriteMeal(meal)
}