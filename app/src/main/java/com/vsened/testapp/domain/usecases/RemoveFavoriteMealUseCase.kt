package com.vsened.testapp.domain.usecases

import com.vsened.testapp.domain.repository.Repository
import javax.inject.Inject

class RemoveFavoriteMealUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: String) = repository.removeFavoriteMeal(id)
}