package com.example.pcbuilder.navigation

//Crear el navhost y enlazar las rutas a los composables LoginScreen y RegistroScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pcbuilder.ui.screens.LoginScreen
import com.example.pcbuilder.ui.screens.RegistroScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pcbuilder.ui.screens.AddEditProductScreen
import com.example.pcbuilder.ui.screens.AdminDashboardScreen
import com.example.pcbuilder.ui.screens.CatalogScreen
import com.example.pcbuilder.viewmodel.AdminViewModel
import com.example.pcbuilder.viewmodel.CatalogoViewModel

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



        composable(route = AppRoutes.CATALOG_SCREEN) {
            val viewModel: CatalogoViewModel = viewModel()
            CatalogScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = AppRoutes.ADMIN_DASHBOARD) {
            val viewModel: AdminViewModel = viewModel()
            AdminDashboardScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = AppRoutes.ADD_EDIT_PRODUCT_ROUTE,
            arguments = listOf(
                navArgument(AppRoutes.PRODUCT_ID_ARG) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val viewModel: AdminViewModel = viewModel()
            val productIdString = backStackEntry.arguments?.getString(AppRoutes.PRODUCT_ID_ARG)

            AddEditProductScreen(
                navController = navController,
                viewModel = viewModel,
                productId = productIdString?.toIntOrNull()
            )
        }
    }
}