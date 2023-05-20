package com.fao.orderfy.datos.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fao.orderfy.datos.Entidades.Producto

@Dao
interface DaoProducto {
    @Query("INSERT INTO producto (idTienda, nombre, descripcion, precio, imagen, disponibilidad, tiempoEstimado) SELECT idTienda, :nombre, :descripcion, :precio, :imagen, 1, :tiempoEstimado FROM vendedor WHERE idVendedor = :idVendedor")
    suspend fun insertarProducto(idVendedor: Int, nombre: String, descripcion: String, precio: Double, imagen: String, tiempoEstimado: String)


    @Query("UPDATE producto SET nombre = :nombre, descripcion = :descripcion, precio = :precio, imagen = :imagen, disponibilidad = :disponibilidad, tiempoEstimado = :tiempoEstimado WHERE idProducto = :idProducto")
    suspend fun editarProducto(idProducto: Int, nombre: String, descripcion: String, precio: Double, imagen: String, disponibilidad: Int, tiempoEstimado: String)


    @Query("DELETE FROM producto WHERE idProducto = :idProducto")
    suspend fun eliminarProducto(idProducto: Int)

    @Query("SELECT * FROM producto WHERE idTienda = :idTienda")
    suspend fun consultarProductosTienda(idTienda: Int): List<Producto>

    @Query("UPDATE producto SET disponibilidad = :disponibilidad WHERE idProducto = :idProducto")
    suspend fun cambiarDisponibilidadProducto(idProducto: Int, disponibilidad: Int)
}
