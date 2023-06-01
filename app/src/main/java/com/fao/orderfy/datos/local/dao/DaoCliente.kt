package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Administrador
import com.fao.orderfy.datos.Entidades.Cliente

@Dao
interface DaoCliente {
    @Query("SELECT * FROM cliente")
    suspend fun consultarCliente(): MutableList<Cliente>

    @Query("INSERT INTO cliente (nombre, apellido, nombreUsuario, pwd) VALUES (:nombre, :apellido, :nombreUsuario, :pwd)")
    suspend fun registrarCliente(
        nombre: String,
        apellido: String,
        nombreUsuario: String,
        pwd: String
    )

    @Query("UPDATE cliente SET nombre = :nombre, apellido = :apellido, nombreUsuario = :nombreUsuario, pwd = :pwd WHERE idCliente = :idCliente")
    suspend fun editarCliente(
        idCliente: Int,
        nombre: String,
        apellido: String,
        nombreUsuario: String,
        pwd: String
    )

    @Query("DELETE FROM cliente WHERE idCliente = :idCliente")
    suspend fun eliminarCliente(idCliente: Int)

    @Query("SELECT * FROM cliente WHERE nombreUsuario = :nombreUsuario")
    suspend fun getClienteByNombreUsuario(nombreUsuario: String): Cliente?

    @Query("SELECT CASE WHEN EXISTS(SELECT * FROM cliente WHERE nombreUsuario = :nombreUsuario AND pwd = :pwd) THEN 1 ELSE 0 END")
    suspend fun authenticateCliente(nombreUsuario: String, pwd: String): Boolean

    @Query("SELECT COUNT(*) FROM cliente")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM cliente WHERE nombreUsuario = :nombreUsuario")
    suspend fun contarUsuarioPorNombre(nombreUsuario: String): Int

    @Query("SELECT idCliente FROM cliente WHERE nombreUsuario = :nombreUsuario")
    suspend fun obtenerIdPorNombreUsuario(nombreUsuario: String): Int


}