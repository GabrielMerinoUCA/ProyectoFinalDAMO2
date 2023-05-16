package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Producto

@Dao
interface DaoProducto {
    @Query("SELECT * FROM producto")
    suspend fun obtenerProductos(): MutableList<Producto>

    @Insert
    suspend fun agregarProducto(producto: Producto)

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)
}
