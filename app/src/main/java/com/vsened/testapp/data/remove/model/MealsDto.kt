package com.vsened.testapp.data.remove.model

import com.google.gson.annotations.SerializedName

data class MealsDto(
    @SerializedName("meals")
    val meals: List<MealDto>
)
