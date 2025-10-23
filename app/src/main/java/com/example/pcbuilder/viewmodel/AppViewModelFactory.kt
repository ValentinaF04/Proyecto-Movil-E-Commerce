package com.example.pcbuilder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pcbuilder.data.dao.UserDao
import com.example.pcbuilder.data.dao.ProductDao

//Incluir todos los DAOs necesarios

class AppViewModelFactory (
    private val userDao: UserDao,
    private val productDao: ProductDao
    //Crear variable cartDao una vez esté creado el carrito
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistroViewModel(userDao) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userDao) as T
        }

        if (modelClass.isAssignableFrom(CatalogoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatalogoViewModel(productDao) as T
        }


        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminViewModel(productDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}