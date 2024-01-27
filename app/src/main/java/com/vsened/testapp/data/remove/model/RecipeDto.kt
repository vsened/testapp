package com.vsened.testapp.data.remove.model

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("meals")
    val meals: List<DetailMealDto>
)