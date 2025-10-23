package com.example.pcbuilder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pcbuilder.navigation.AppRoutes
import com.example.pcbuilder.viewmodel.AppViewModelFactory
import com.example.pcbuilder.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import com.example.pcbuilder.PCBuilderApplication 
import com.example.pcbuilder.data.SessionManager

//interfaz visual

@Composable
fun LoginScreen(navController: NavController){

    val context = LocalContext.current
    val db = (context.applicationContext as PCBuilderApplication).database
    val factory = AppViewModelFactory(db.userDao(), db.productDao(), db.cartDao)
    val viewModel: LoginViewModel = viewModel (factory = factory)
    val estado by viewModel.estado.collectAsState()
    val scope = rememberCoroutineScope()
    //Obtener una insancia del SessionManager
    val sessionManager = remember {SessionManager(context)}

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = estado.correo, onValueChange = viewModel::onCorreoChange, label = { Text("Correo") })
        OutlinedTextField(value = estado.clave, onValueChange = viewModel::onClaveChange, label = { Text("Contraseña") })

        //mostrar error
        if (estado.error != null){
            Text(estado.error!!, color = MaterialTheme.colorScheme.error)
        }

        Button(onClick = {
            scope.launch {
                viewModel.iniciarSesion { user ->
                if(user != null){
                    sessionManager.saveUserId(user.uid)
    
                    val ruta = if(user.isAdmin){
                        AppRoutes.ADMIN_DASHBOARD
                    }else{
                        AppRoutes.CATALOGO_SCREEN
                    }
                    navController.navigate(ruta){
                        popUpTo(AppRoutes.LOGIN_SCREEN) { inclusive = true }
                    }
                }
            }
        }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Ingresar")
        }
        
        TextButton(
            onClick = { 
                navController.navigate(AppRoutes.REGISTRO_SCREEN)}) 
        {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

