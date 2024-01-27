package com.vsened.testapp.data.mappers

import com.vsened.testapp.data.local.model.DetailMealEntity
import com.vsened.testapp.data.local.model.FavoriteMealEntity
import com.vsened.testapp.data.local.model.MealEntity
import com.vsened.testapp.data.remove.model.DetailMealDto
import com.vsened.testapp.data.remove.model.MealDto
import com.vsened.testapp.domain.model.DetailMeal
import com.vsened.testapp.domain.model.FavoriteMeal
import com.vsened.testapp.domain.model.Meal

fun MealDto.toMealEntity(): MealEntity {
    return MealEntity(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

fun MealEntity.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

fun MealEntity.toDetailMeal(): DetailMeal {
    return DetailMeal(
        idMeal = idMeal,
        strArea = "",
        strCategory = "",
        strInstructions = strInstructions!!,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strYoutube = "",
        strNote = ""
    )
}

fun DetailMealEntity.toDetailMeal(): DetailMeal {
    return DetailMeal(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strYoutube = strYoutube,
    )
}

fun DetailMealDto.toDetailMealEntity(): DetailMealEntity {
    return DetailMealEntity(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strYoutube = strYoutube,
    )
}

fun FavoriteMealEntity.toDetailMeal(): DetailMeal {
    return DetailMeal(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strYoutube = strYoutube,
        strNote = strNote
    )
}

fun FavoriteMealEntity.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

fun DetailMeal.toFavoriteMealEntity(): FavoriteMealEntity {
    return FavoriteMealEntity(
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strYoutube = strYoutube,
        strNote = strNote
    )
}

