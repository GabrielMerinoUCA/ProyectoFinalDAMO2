package com.fao.orderfy.datos.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DaoProducto {

    /**
     * Insertar Producto a una tienda por medio del id vendedor porque un vendedor solo tiene una tienda
     */
    @FormUrlEncoded
    @POST("producto-insertar.php")
    fun insertarProducto(
        @Field("idVendedor") idVendedor: Int,
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("precio") precio: Float,
        @Field("imagen") imagen: ByteArray,
        @Field("disponibilidad") disponibilidad: Boolean,
        @Field("tiempoEstimado") tiempoEstimado: String
    ): Call<JsonArray>

    /**
     * Eliminar producto del registro
     */
    @FormUrlEncoded
    @POST("producto-eliminar.php")
    fun eliminarProducto(
        @Field("idProducto") idProducto: Int
    ): Call<JsonArray>

    /**
     * Editar Producto del registro
     */
    @FormUrlEncoded
    @POST("producto-editar.php")
    fun editarProducto(
        @Field("idProducto") idProducto: Int,
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("precio") precio: Float,
        @Field("imagen") imagen: ByteArray,
        @Field("disponibilidad") disponibilidad: Boolean,
        @Field("tiempoEstimado") tiempoEstimado: String
    ): Call<JsonArray>

    /**
     * Consultar registro de productos para una tienda
     */
    @FormUrlEncoded
    @POST("producto-consultar.php")
    fun consultarProductosTienda(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>

    /**
     *
     */

}