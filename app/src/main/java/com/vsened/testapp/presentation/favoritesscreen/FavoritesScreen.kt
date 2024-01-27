package com.vsened.testapp.presentation.favoritesscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsened.testapp.R
import com.vsened.testapp.presentation.screens.Screen
import com.vsened.testapp.presentation.utils.BottomNavigationItem
import com.vsened.testapp.presentation.utils.MealItem

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val bottomNavigation = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.recipe_title),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_food_foreground),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_food_outlined_foreground),
            route = Screen.MainScreen.route
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.favorites_title),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_heart_foreground),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_heart_outlined_foreground),
            route = Screen.FavoritesScreen.route
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(1)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavigation.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {

            items(state.meals.size) { index ->
                MealItem(meal = state.meals[index]) { meal ->
                    navController.navigate(
                        Screen.DetailFavoritesScreen.route
                                + "?id=${meal.idMeal}"
                    )
                }
            }

        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

