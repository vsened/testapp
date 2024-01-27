package com.vsened.testapp.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.vsened.testapp.presentation.screens.Screen.FavoritesScreen.route

sealed class Screen(
    val route: String,
    val title: String? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
) {
    data object MainScreen : Screen(
        route = "main_screen",
        title = "Рецепты",
        selectedIcon = Icons.Default.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart
    )

    data object FavoritesScreen : Screen(
        route = "favorites_screen",
        title = "Избранное",
        selectedIcon = Icons.Default.Favorite,
        unselectedIcon = Icons.Outlined.Favorite
    )

    data object DetailScreen: Screen("detail_screen")

    data object DetailFavoritesScreen: Screen("detail_favorite_screen")
}