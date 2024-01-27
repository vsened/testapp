package com.vsened.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vsened.testapp.data.local.model.DetailMealEntity
import com.vsened.testapp.data.local.model.FavoriteMealEntity
import com.vsened.testapp.data.local.model.MealEntity

@Database(
    entities = [MealEntity::class, FavoriteMealEntity::class, DetailMealEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: AppDao
}