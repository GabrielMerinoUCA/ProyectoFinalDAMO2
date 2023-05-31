package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiProducto {

    /**
     * Insertar Producto a una tienda por medio del id vendedor porque un vendedor solo tiene una tienda
     */
    @FormUrlEncoded
    @POST("producto-insertar.php")
    fun insertarProducto(
        @Field("idTienda") idTienda: Int,
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("precio") precio: Float,
        @Field("imagen") imagen: String,
        @Field("tiempoEstimado") tiempoEstimado: Int
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
        @Field("imagen") imagen: String,
        @Field("disponibilidad") disponibilidad: Boolean,
        @Field("tiempoEstimado") tiempoEstimado: Int
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
     * Cambiar las disponibilidad del producto al valor opuesto del valor actual.
     */
    @FormUrlEncoded
    @POST("producto-cambiar-disponibilidad.php")
    fun cambiarDisponibilidadProducto(
        @Field("idProducto") idProducto:Int
    ): Call<JsonArray>


}