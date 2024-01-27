package com.vsened.testapp.domain.usecases

import com.vsened.testapp.domain.repository.Repository
import javax.inject.Inject

class GetFavoriteMealByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: String) = repository.getFavoriteMealById(id)
}