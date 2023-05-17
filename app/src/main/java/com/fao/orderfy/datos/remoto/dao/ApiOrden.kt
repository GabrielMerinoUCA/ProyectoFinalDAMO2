package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiOrden {
    /**
     * Consultar todas las ordenes del cliente, ordenadas por el estado de manera ascendiente
     */
    @FormUrlEncoded
    @POST("orden-consultar-cliente.php")
    fun consultarOrdenCliente(
        @Field("idCliente") idCliente:Int
    ): Call<JsonArray>

    /**
     * Consultar todas las ordenes hacia varios productos de una tienda en especifico con el estado
     * pendiente
     */
    @FormUrlEncoded
    @POST("orden-consultar-tienda-pendiente.php")
    fun consultarOrdenPendienteTienda(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>

    /**
     * Consultar todas las ordenes hacia varios productos de una tienda en especifico
     */
    @FormUrlEncoded
    @POST("orden-consultar-tienda.php")
    fun consultarOrdenTienda(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>

    /**
     * Cambiar el estado actual de la reservacion a Lista. Solamente se deben de cambiar a este
     * estado las ordenes que esten en estado pendiente
     */
    @FormUrlEncoded
    @POST("orden-editar-lista.php")
    fun cambiarEstadoOrdenLista(
        @Field("idOrden") idOrden: Int
    ): Call<JsonArray>

    /**
     * Cambiar el estado actual de la reservacion a Reclamada. Solamente se deben de cambiar a este
     * estado las ordenes que esten en estado lista.
     */
    @FormUrlEncoded
    @POST("orden-editar-reclamada.php")
    fun cambiarEstadoOrdenReclamada(
        @Field("idOrden") idOrden: Int,
        @Field("horaReclamo") horaReclamo: String
    ): Call<JsonArray>

    /**
     * Insertar nueva orden de un cliente hacia un producto al registro
     */
    @FormUrlEncoded
    @POST("orden-insertar.php")
    fun insertarOrden(
        @Field("horaPedido") horaPedido: String,
        @Field("idCliente") idCliente: Int,
        @Field("idProducto") idProducto: Int,
        @Field("cantidad") cantidad: Int
    ): Call<JsonArray>
}