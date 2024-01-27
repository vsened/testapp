package com.vsened.testapp.presentation.detailscreen

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vsened.testapp.R
import com.vsened.testapp.presentation.ui.theme.cruinn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailMealViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val imageState = viewModel.downloadImageState
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.meal?.strMeal ?: "",
                        fontFamily = cruinn,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_icon_label)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (state.isFavorite) {
                                viewModel.removeFavorite(state.meal!!.idMeal)
                            } else {
                                viewModel.addToFavorite(state.meal!!)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (state.isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Rounded.FavoriteBorder
                            },
                            contentDescription = stringResource(R.string.favorite_icon)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background),

            ) {

            state.meal?.let { meal ->
                LazyColumn(
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = stringResource(id = R.string.meal_image_description),
                                modifier = Modifier.padding(10.dp),
                                error = painterResource(id = R.drawable.error_image)
                            )
                            val request = ImageRequest.Builder(context)
                                .data(meal.strMealThumb)
                                .build()
                            val imageLoader = ImageLoader(context)
                            val savedImage = remember {
                                mutableStateOf<Bitmap?>(null)
                            }
                            LaunchedEffect(key1 = meal.strMealThumb) {
                                val bitmap = imageLoader.execute(request = request).drawable
                                savedImage.value = bitmap?.toBitmap()
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    onClick = {
                                        if (savedImage.value != null) {
                                            viewModel.shareImage(context, savedImage.value!!)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = stringResource(
                                            R.string.share_icon_description
                                        )
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (savedImage.value != null) {
                                            viewModel.saveImage(context, savedImage.value!!)
                                        }

                                    }
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(
                                            id = R.drawable.ic_download_foreground
                                        ),
                                        contentDescription = stringResource(
                                            R.string.download_icon_description
                                        ),
                                        modifier = Modifier.size(36.dp)
                                    )

                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = meal.strInstructions,
                                fontFamily = cruinn,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
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
}
