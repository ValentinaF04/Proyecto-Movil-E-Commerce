package com.example.yourapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourapp.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Insertamos un usuario en la tabla 'users' 
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    //Buscamos un usuario por el correo electronico

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
    
    //Seleccionamos atodos los usuarios de la BD
    
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
}