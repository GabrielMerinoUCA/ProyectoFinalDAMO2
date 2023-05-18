package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Orden

@Dao
interface DaoOrden {
    @Query("SELECT * FROM orden WHERE idCliente = :idCliente ORDER BY estado ASC")
    suspend fun consultarOrdenCliente(idCliente: Int): MutableList<Orden>

    @Query("SELECT * FROM orden WHERE idProducto IN (SELECT idProducto FROM producto WHERE idTienda = :idTienda) AND estado = '0'")
    suspend fun consultarOrdenPendienteTienda(idTienda: Int): MutableList<Orden>

    @Query("SELECT * FROM orden WHERE idProducto IN (SELECT idProducto FROM producto WHERE idTienda = :idTienda)")
    suspend fun consultarOrdenTienda(idTienda: Int): MutableList<Orden>


    @Query("UPDATE orden SET estado = '1' WHERE idOrden = :idOrden")
    suspend fun cambiarEstadoOrdenLista(idOrden: Int)

    @Query("UPDATE orden SET horaReclamo = :horaReclamo, estado = '2' WHERE idOrden = :idOrden")
    suspend fun cambiarEstadoOrdenReclamada(idOrden: Int, horaReclamo: String)
    @Query("INSERT INTO orden (horaPedido, idCliente, idProducto, cantidad) VALUES (:horaPedido, :idCliente, :idProducto, :cantidad)")
    suspend fun insertarOrden(horaPedido: String, idCliente: Int, idProducto: Int, cantidad: Int)
}
