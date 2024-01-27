package com.vsened.testapp.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.vsened.testapp.R
import com.vsened.testapp.domain.model.Meal
import com.vsened.testapp.presentation.ui.theme.cruinn

@Composable
fun MealItem(
    meal: Meal,
    showFavorites: Boolean = false,
    isFavorite: Boolean = false,
    onItemClick: (Meal) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(150.dp)
            .clickable {
                onItemClick(meal)
            },
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val request = ImageRequest.Builder(LocalContext.current)
                    .data(meal.strMealThumb)
                    .error(R.drawable.error_image)
                    .build()
                val painter = rememberAsyncImagePainter(request)
                Image(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.meal_image_description)
                )

                Text(
                    text = meal.strMeal.uppercase(),
                    fontSize = 15.sp,
                    fontFamily = cruinn,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
            }
            if (showFavorites && isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.is_favorite_icon_description),
                    modifier = Modifier
                        .padding(15.dp)
                        .background(color = Color(0x8FE0D5D4), shape = CircleShape)
                        .padding(5.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }


    }
}