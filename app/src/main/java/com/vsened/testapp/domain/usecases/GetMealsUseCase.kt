package com.vsened.testapp.domain.usecases

import com.vsened.testapp.domain.repository.Repository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getMeals()
}