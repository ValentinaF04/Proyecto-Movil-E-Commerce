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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pcbuilder.navigation.AppRoutes

//interfaz visual

@Composable
fun LoginScreen(navController: NavController){

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Correo") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Contraseña") })

        Button(onClick = {  }, modifier = Modifier.fillMaxWidth()) {
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

