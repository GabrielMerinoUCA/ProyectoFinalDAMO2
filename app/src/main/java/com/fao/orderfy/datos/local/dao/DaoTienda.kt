package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Tienda

@Dao
interface DaoTienda {
    @Query("SELECT * FROM tienda")
    suspend fun obtenerTiendas(): MutableList<Tienda>

    @Insert
    suspend fun agregarTienda(tienda: Tienda)

    @Update
    suspend fun actualizarTienda(tienda: Tienda)

    @Delete
    suspend fun eliminarTienda(tienda: Tienda)
}
