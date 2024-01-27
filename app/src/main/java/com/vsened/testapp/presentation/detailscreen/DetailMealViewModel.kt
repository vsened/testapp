package com.vsened.testapp.presentation.detailscreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsened.testapp.domain.model.DetailMeal
import com.vsened.testapp.domain.usecases.AddFavoriteMealUseCase
import com.vsened.testapp.domain.usecases.CheckMealInFavoriteUseCase
import com.vsened.testapp.domain.usecases.GetLocalMealByIdUseCase
import com.vsened.testapp.domain.usecases.RemoveFavoriteMealUseCase
import com.vsened.testapp.presentation.utils.ImageDownloadState
import com.vsened.testapp.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject


@HiltViewModel
class DetailMealViewModel @Inject constructor(
    private val getLocalMealByIdUseCase: GetLocalMealByIdUseCase,
    private val checkFavoritesMealsUseCase: CheckMealInFavoriteUseCase,
    private val addFavoriteMealUseCase: AddFavoriteMealUseCase,
    private val removeFavoriteMealUseCase: RemoveFavoriteMealUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DetailMealState())

    var downloadImageState by mutableStateOf(ImageDownloadState())

    init {
        savedStateHandle.get<String>(MEAL_ID)?.let { id ->
            getDetailMealById(id)
        }
    }

    private fun getDetailMealById(id: String) {
        getLocalMealByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isFavorite = result.isLoading
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        meal = result.data,
                        isFavorite = checkFavoritesMealsUseCase(result.data!!.idMeal)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addToFavorite(meal: DetailMeal) {
        viewModelScope.launch {
            addFavoriteMealUseCase(meal)
            state = state.copy(
                isFavorite = true
            )
        }
    }

    fun removeFavorite(id: String) {
        viewModelScope.launch {
            removeFavoriteMealUseCase(id)
            state = state.copy(
                isFavorite = false
            )
        }
    }

    fun shareImage(context: Context, bitmap: Bitmap) {
        val share = Intent(Intent.ACTION_SEND)
        share.setType("image/png")
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator + "Recipes" + File.separator +
                "${state.meal?.strMeal?.replace(" ", "_")}_${System.currentTimeMillis()}.png"
        val f = File(path)
        try {
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
        context.startActivity(Intent.createChooser(share, "Share Image"))

    }



    fun saveImage(context: Context, bitmap: Bitmap) {
        val fileOutputStream: OutputStream
        val file: File?
        val root = Environment.getExternalStorageDirectory().absolutePath
        val myDir = File("$root/Recipes")
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
        val fName = "${state.meal?.strMeal?.replace(" ", "_")}.png"
        file = File(myDir, fName)
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            Toast.makeText(context, "Image Saved Successfully", Toast.LENGTH_SHORT).show()
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            Log.d("viewmodel", e.message.toString())
        }
    }

    companion object {
        private const val MEAL_ID = "id"
    }
}