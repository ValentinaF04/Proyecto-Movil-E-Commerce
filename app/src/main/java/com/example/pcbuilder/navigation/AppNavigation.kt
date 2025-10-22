package com.example.pcbuilder.navigation

//Crear el navhost y enlazar las rutas a los composables LoginScreen y RegistroScreen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pcbuilder.ui.screens.LoginScreen 
import com.example.pcbuilder.ui.screens.RegistroScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN_SCREEN
    ) {
        composable(route = AppRoutes.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }

        composable(route = AppRoutes.REGISTRO_SCREEN) {
            RegistroScreen(navController = navController)
        }
    }
}
