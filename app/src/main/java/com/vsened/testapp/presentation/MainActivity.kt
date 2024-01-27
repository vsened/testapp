package com.vsened.testapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vsened.testapp.presentation.detailfavoritescreen.DetailFavoritesScreen
import com.vsened.testapp.presentation.detailscreen.DetailScreen
import com.vsened.testapp.presentation.favoritesscreen.FavoritesScreen
import com.vsened.testapp.presentation.mainscreen.MainScreen
import com.vsened.testapp.presentation.screens.Screen
import com.vsened.testapp.presentation.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isStoragePermissionGranted = false
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MainScreen.route
                ) {
                    composable(
                        route = Screen.MainScreen.route
                    ) {
                        MainScreen(navController)
                    }
                    composable(
                        route = Screen.DetailScreen.route +
                                "?id={id}"
                    ) {
                        DetailScreen(navController)
                    }
                    composable(
                        route = Screen.FavoritesScreen.route
                    ) {
                        FavoritesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.DetailFavoritesScreen.route +
                                "?id={id}"
                    ) {
                        DetailFavoritesScreen(navController)
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
            isStoragePermissionGranted = permissions[Manifest.permission.MANAGE_EXTERNAL_STORAGE] ?: isStoragePermissionGranted
        }

        requestPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestPermissions() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        isWritePermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        isStoragePermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequestList = ArrayList<String>()
        if (!isReadPermissionGranted) permissionRequestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!isWritePermissionGranted) permissionRequestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isStoragePermissionGranted) permissionRequestList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)

        if (permissionRequestList.isNotEmpty()) permissionLauncher.launch(permissionRequestList.toTypedArray())
    }
}