package com.example.pcbuilder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuilder.data.dao.ProductDao
import com.example.pcbuilder.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AdminViewModel(private val productDao: ProductDao) : ViewModel() {

    val productos: Flow<List<Product>> = productDao.getAllProducts()

    fun getProduct(id: Int): Flow<Product?> {
        return productDao.getProductById(id)
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.deleteProduct(product)
        }
    }


    fun insertProduct(name: String, description: String, price: Double, stock: Int, id: Int = 0) {
        viewModelScope.launch {
            if (name.isNotBlank() && description.isNotBlank()) {
                val product = Product(id = id, name = name, description = description, price = price, stock = stock)
                productDao.insertProduct(product)
            }
        }
    }
}