package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Orden

@Dao
interface DaoOrden {
    @Query("SELECT * FROM orden")
    suspend fun obtenerOrdenes(): MutableList<Orden>

    @Insert
    suspend fun agregarOrden(orden: Orden)

    @Update
    suspend fun actualizarOrden(orden: Orden)

    @Delete
    suspend fun eliminarOrden(orden: Orden)
}
