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
    suspend fun consultarTienda(): MutableList<Tienda>


    @Query("UPDATE tienda SET nombre = :nombre, horaApertura = :horaApertura, horaCierre = :horaCierre, logo = :logo WHERE idTienda = :idTienda")
    suspend fun editarTienda(idTienda: Int, nombre: String, horaApertura: String, horaCierre: String, logo: String): Int


    @Query("DELETE FROM tienda WHERE idTienda = :idTienda")
    suspend fun eliminarTienda(idTienda: Int): Int

    @Query("UPDATE tienda SET estado = CASE WHEN estado = 0 THEN 1 ELSE 0 END WHERE idTienda = :idTienda")
    suspend fun cambiarEstadoTienda(idTienda: Int): Int

    @Query("SELECT COUNT(*) FROM tienda")
    suspend fun getCount(): Int

    @Insert
    suspend fun insertarTienda(tienda: Tienda)

    @Query("SELECT COUNT(*) FROM tienda WHERE nombre = :tienda")
    suspend fun contarUsuarioPorNombre(tienda: String): Int

    @Query("SELECT idTienda FROM tienda WHERE nombre = :nombreTienda")
    suspend fun obtenerIdPorNombreUsuario(nombreTienda: String): Int


}
