package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Registro

@Dao
interface DaoRegistro {
    @Query("SELECT * FROM registro")
    suspend fun obtenerRegistros(): MutableList<Registro>

    @Insert
    suspend fun agregarRegistro(registro: Registro)

    @Update
    suspend fun actualizarRegistro(registro: Registro)

    @Delete
    suspend fun eliminarRegistro(registro: Registro)
}
