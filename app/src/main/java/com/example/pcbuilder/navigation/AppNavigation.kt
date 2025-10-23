package com.example.pcbuilder.navigation

//Crear el navhost y enlazar las rutas a los composables LoginScreen y RegistroScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pcbuilder.ui.screens.LoginScreen 
import com.example.pcbuilder.ui.screens.RegistroScreen
import androidx.compose.ui.Modifier

@Composable
fun AppNavigation(modifier: Modifier = Modifier){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN_SCREEN,
        modifier = modifier
    ) {
        composable(route = AppRoutes.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }

        composable(route = AppRoutes.REGISTRO_SCREEN) {
            RegistroScreen(navController = navController)
        }

        composable(route = AppRoutes.CART_SCREEN) {
            CartScreen(navController = navController)
        }

        composable(route = AppRoutes.CHECKOUT_SCREEN) {
            CheckoutScreen(navController = navController)
        }

        composable(route = AppRoutes.COMPRA_EXITOSA_SCREEN) {
            // CompraExitosaScreen(navController = navController)
        }

    }
}
