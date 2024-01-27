package com.vsened.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vsened.testapp.data.local.model.DetailMealEntity
import com.vsened.testapp.data.local.model.FavoriteMealEntity
import com.vsened.testapp.data.local.model.MealEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(
        meals: List<MealEntity>
    )

    @Query("SELECT * FROM mealentity")
    suspend fun getMealsList(): List<MealEntity>

    @Query("SELECT * FROM mealentity WHERE idMeal= :id")
    suspend fun getMealById(id: String): MealEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealToFavorites(
        meal: FavoriteMealEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMeal(
        detailMeal: DetailMealEntity
    )

    @Query("SELECT * FROM detailmealentity WHERE idMeal= :id")
    suspend fun getDetailMealById(id: String): DetailMealEntity

    @Query("SELECT * FROM favoritemealentity")
    suspend fun getFavoriteMealsList(): List<FavoriteMealEntity>

    @Query("SELECT * FROM favoritemealentity WHERE idMeal = :id")
    suspend fun getFavoriteMealById(id: String): FavoriteMealEntity

    @Query("DELETE FROM favoritemealentity WHERE idMeal = :id")
    suspend fun deleteFavoriteMealById(id: String)

    @Query("SELECT EXISTS(SELECT * FROM favoritemealentity WHERE idMeal = :id)")
    suspend fun isFavoriteMeal(id: String): Boolean
}