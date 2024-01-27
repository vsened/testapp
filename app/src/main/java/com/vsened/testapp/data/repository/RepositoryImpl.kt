package com.vsened.testapp.data.repository

import com.vsened.testapp.data.local.AppDatabase
import com.vsened.testapp.data.mappers.toDetailMeal
import com.vsened.testapp.data.mappers.toDetailMealEntity
import com.vsened.testapp.data.mappers.toFavoriteMealEntity
import com.vsened.testapp.data.mappers.toMeal
import com.vsened.testapp.data.mappers.toMealEntity
import com.vsened.testapp.data.remove.ApiService
import com.vsened.testapp.domain.model.DetailMeal
import com.vsened.testapp.domain.model.Meal
import com.vsened.testapp.domain.repository.Repository
import com.vsened.testapp.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService,
    db: AppDatabase
): Repository {
    private val dao = db.dao
    override suspend fun addFavoriteMeal(meal: DetailMeal) {
        dao.insertMealToFavorites(meal.toFavoriteMealEntity())
    }

    override suspend fun removeFavoriteMeal(id: String) {
        dao.deleteFavoriteMealById(id)
    }

    override suspend fun getMeals(): Flow<Resource<List<Meal>>> {
        return flow {
            emit(Resource.Loading(true))

            var localMeals = dao.getMealsList()
            if (localMeals.isEmpty()) {
                val remoteMeals = try {
                    api.getMeals()
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error(e.message ?: "Error"))
                    null
                }
                remoteMeals?.let { rMeals ->
                    dao.insertMeals(rMeals.meals.map { it.toMealEntity() })
                }
                localMeals = dao.getMealsList()
                localMeals.forEach {
                    val remoteDetailMeal = try {
                        api.getMealById(it.idMeal.toInt())
                    } catch (e: IOException) {
                        e.printStackTrace()
                        emit(Resource.Error("Couldn't load data"))
                        null
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        emit(Resource.Error("Couldn't load data"))
                        null
                    } catch (e: Exception) {
                        e.printStackTrace()
                        emit(Resource.Error(e.message ?: "Error"))
                        null
                    }
                    remoteDetailMeal.let {recipes ->
                        val detailMealDto = recipes?.meals?.get(0)
                        dao.insertDetailMeal(detailMealDto!!.toDetailMealEntity())
                    }
                }
            }
            localMeals.let {
                emit(Resource.Success(it.map { meal -> meal.toMeal() }))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getFavoriteMeals(): Flow<Resource<List<Meal>>> {
        return flow {
            emit(Resource.Loading(true))
            val localFavoriteMeals = dao.getFavoriteMealsList()
            emit(Resource.Success(localFavoriteMeals.map { favoriteMeal -> favoriteMeal.toMeal() }))
            emit(Resource.Loading(false))
        }
    }

    override fun getRemoveMealById(id: String): Flow<Resource<DetailMeal>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteMeal = try {
                api.getMealById(id.toInt())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Error"))
                null
            }
            remoteMeal.let {
                val detailMealDto = it?.meals?.get(0)
                dao.insertDetailMeal(detailMealDto!!.toDetailMealEntity())
            }
            val localMeal = dao.getDetailMealById(id)
            localMeal.let {
                emit(Resource.Success(it.toDetailMeal()))
            }
            emit(Resource.Loading(false))
        }
    }

    override fun getLocalMealById(id: String): Flow<Resource<DetailMeal>> {
        return flow {
            emit(Resource.Loading(true))
            val localMeal = dao.getDetailMealById(id)
            localMeal.let {
                emit(Resource.Success(it.toDetailMeal()))
            }
        }

    }

    override fun getFavoriteMealById(id: String): Flow<Resource<DetailMeal>> {
        return flow {
            emit(Resource.Loading(true))
            val meal = dao.getFavoriteMealById(id)
            emit(Resource.Success(meal.toDetailMeal()))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun isFavoriteMeal(id: String): Boolean {
        return dao.isFavoriteMeal(id)
    }


}