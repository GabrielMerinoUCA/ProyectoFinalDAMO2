package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Vendedor

@Dao
interface DaoVendedor {
    @Query("SELECT * FROM vendedor")
    suspend fun obtenerVendedores(): MutableList<Vendedor>

    @Insert
    suspend fun agregarVendedor(vendedor: Vendedor)

    @Update
    suspend fun actualizarVendedor(vendedor: Vendedor)

    @Delete
    suspend fun eliminarVendedor(vendedor: Vendedor)
}
