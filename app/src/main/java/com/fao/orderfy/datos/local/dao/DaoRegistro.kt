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
    suspend fun consultarRegistro(): MutableList<Registro>

    @Query("INSERT INTO registro(nombre, apellido, nombreUsuario, nombreTienda, horaApertura, horaCierre, pwd, estado) VALUES (:nombre, :apellido, :nombreUsuario, :nombreTienda, :horaApertura, :horaCierre, :pwd, :estado)")
    suspend fun insertRegistro(
        nombre: String,
        apellido: String,
        nombreUsuario: String,
        nombreTienda: String,
        horaApertura: String,
        horaCierre: String,
        pwd: String,
        estado: Int
    )


    @Query("UPDATE registro SET estado = 1 WHERE idRegistro = :idRegistro")
    suspend fun aceptarRegistro(idRegistro: Int)

    @Query("UPDATE registro SET estado = 2 WHERE idRegistro = :idRegistro")
    suspend fun rechazarRegistro(idRegistro: Int): Int
}
