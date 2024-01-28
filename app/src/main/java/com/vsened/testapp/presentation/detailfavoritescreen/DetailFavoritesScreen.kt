package com.vsened.testapp.presentation.detailfavoritescreen

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.graphics.Bitmap
import android.icu.util.Calendar
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.vsened.testapp.presentation.MainActivity
import com.vsened.testapp.presentation.screens.Screen
import com.vsened.testapp.presentation.ui.theme.cruinn
import com.vsened.testapp.presentation.utils.AlarmReceiver
import com.vsened.testapp.presentation.utils.NotificationTime
import kotlinx.coroutines.launch


@SuppressLint("ScheduleExactAlarm")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailFavoritesScreen(
    navController: NavController,
    viewModel: DetailFavoriteMealViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val note = remember {
        mutableStateOf("")
    }
    val noteState = remember {
        mutableStateOf("")
    }
    val editMode = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            viewModel.stateNote.collect { string ->
                noteState.value = string
            }
        }
    }
    val time = remember {
        mutableStateOf(NotificationTime.MINUTES)
    }
    val shouldSetNotification = remember {
        mutableStateOf(false)
    }
    val builder = AlertDialog.Builder(context)
    val setter = arrayOf(
        NotificationTime.MINUTES,
        NotificationTime.HOUR,
        NotificationTime.DAY,
        NotificationTime.WEEK
    )
    builder
        .setTitle(stringResource(R.string.dialod_title))
        .setSingleChoiceItems(arrayOf("15 минут", "1 час", "1 день", "1 неделя"), 0) { _, which ->
            time.value = setter[which]
        }
        .setPositiveButton(stringResource(R.string.set_notification_label)) { _, _ ->
            val alarmManager = context
                .getSystemService(ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            when (time.value) {
                NotificationTime.MINUTES -> calendar.add(Calendar.MINUTE, 15)
                NotificationTime.HOUR -> calendar.add(Calendar.HOUR, 1)
                NotificationTime.DAY -> calendar.add(Calendar.HOUR, 24)
                NotificationTime.WEEK -> calendar.add(Calendar.HOUR, 168)
            }
            val intent = AlarmReceiver.newIntent(context)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
        .setNegativeButton("Отмена") { _, _ ->

        }
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
                            navController.navigate(Screen.FavoritesScreen.route)
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
                            builder.create().show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = stringResource(
                                R.string.create_notification_button_description
                            ),
                        )
                    }
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
                            OutlinedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Text(
                                        text = stringResource(R.string.note_label),
                                        fontFamily = cruinn,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Absolute.Right
                                    ) {
                                        IconButton(onClick = {
                                            editMode.value = true
                                            note.value = noteState.value
                                        }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = stringResource(R.string.edit_button_description)
                                            )
                                        }
                                        IconButton(onClick = {
                                            editMode.value = false
                                            val newMeal = state.meal.copy(
                                                strNote = note.value
                                            )
                                            viewModel.addToFavorite(newMeal)
                                            viewModel.updateNote(note.value)

                                        }
                                        ) {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.baseline_save_24),
                                                contentDescription = stringResource(R.string.save_button_description)
                                            )
                                        }
                                    }
                                }
                                if (editMode.value) {
                                    OutlinedTextField(
                                        value = note.value,
                                        onValueChange = { new ->
                                            note.value = new
                                        },
                                        minLines = 1,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    )
                                } else {
                                    Text(
                                        text = noteState.value,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        minLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = meal.strInstructions,
                                fontFamily = cruinn,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
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