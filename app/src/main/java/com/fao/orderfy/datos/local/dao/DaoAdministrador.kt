package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Administrador

@Dao
interface DaoAdministrador {

    @Query("Update administrador set  nombre=:nombre, " +
            "apellido=:apellido, nombreUsuario=:nombreUsuario, pwd=:pwd WHERE idAdministrador=:idAdministrador")
    suspend fun actualizarAdministrador(
        idAdministrador: Int,
        nombre: String,
        apellido: String,
        nombreUsuario: String,
        pwd: String,
    )


}
