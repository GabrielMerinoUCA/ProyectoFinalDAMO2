package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Administrador

@Dao
interface DaoAdministrador {
    @Query("SELECT * FROM administrador")
    suspend fun obtenerAdministrador(): MutableList<Administrador>

    @Insert
    suspend fun agregarAdministrador(administrador: Administrador)

    @Update
    suspend fun actualizarAdministrador(administrador: Administrador)

    @Delete
    suspend fun eliminarAdministrador(administrador: Administrador)
}
