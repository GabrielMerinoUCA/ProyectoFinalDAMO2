package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Cliente

@Dao
interface DaoCliente {
    @Query("SELECT * FROM cliente")
    suspend fun obtenerCliente(): MutableList<Cliente>

    @Insert
    suspend fun agregarCliente(cliente: Cliente)

    @Update
    suspend fun actualizarCliente(cliente: Cliente)

    @Delete
    suspend fun eliminarCliente(cliente: Cliente)
}