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

    @Query("UPDATE vendedor SET nombreUsuario = :nombreUsuario, pwd = :pwd WHERE idVendedor = :idVendedor")
    suspend fun updateVendedor(nombreUsuario: String, pwd: String, idVendedor: String)


    @Query("DELETE FROM vendedor WHERE idVendedor = :idVendedor")
    suspend fun deleteVendedor(idVendedor: String)

    // TODO: El autentication que no se como se hace xd
}
