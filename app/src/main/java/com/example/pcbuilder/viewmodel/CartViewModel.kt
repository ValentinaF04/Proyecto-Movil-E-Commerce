package com.example.pcbuilder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcbuilder.data.dao.CartDao
import com.example.pcbuilder.data.dao.ProductDao
import com.example.pcbuilder.data.model.CartItem
import com.example.pcbuilder.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartDao: CartDao,
    private val productDao: ProductDao // Necesitamos ambos
) : ViewModel() {

    //obtiene la lista de items del carrito
    fun getCartItems(userId: Int): Flow<List<CartItem>> {
        return cartDao.getCartItemsForUser(userId)
    }

    //Obtiene detalles de un producto
    fun getProductById(productId: Int): Flow<Product> {
        return productDao.getProductById(productId)
    }

    //añadir al carrito
    fun addItemToCart(userId: Int, productId: Int) {
        viewModelScope.launch {
            // Revisa si el item ya existe
            val existingItem = cartDao.findItemInCart(userId, productId)

            if (existingItem != null) {
                // Si existe, actualiza la cantidad
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                cartDao.insertItem(updatedItem)
            } else {
                // Si no existe, crea uno nuevo
                val newItem = CartItem(userId = userId, productId = productId, quantity = 1)
                cartDao.insertItem(newItem)
            }
        }
    }

    //simular el pago
    suspend fun performCheckout(userId: Int, onCheckoutSuccess: () -> Unit) {
        val items = cartDao.getCartItemsForUser(userId).first() // .first() toma el valor actual del Flow

        //actualizar stock
        for (item in items) {
            val product = productDao.getProductById(item.productId).first()
            if (product.stock >= item.quantity) {
                val updatedProduct = product.copy(stock = product.stock - item.quantity)
                productDao.updateProduct(updatedProduct)
            }
        }

        //limpiar el carrito
        cartDao.clearCart(userId)

        onCheckoutSuccess()
    }
}