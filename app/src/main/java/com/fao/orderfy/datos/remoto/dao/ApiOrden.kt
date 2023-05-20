package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiOrden {

    /**
     * Consultar todas las ordenes de un Cliente, de forma acendente para estado.
     */
    @FormUrlEncoded
    @POST("orden-consultar-cliente.php")
    fun consultarOrdenCliente(
        @Field("idCliente") idCliente: Int
    ): Call<JsonArray>

    /**
     * Consultar ordenes de una tienda (para Vendedor) que hayan sido entregadas
     */
    @FormUrlEncoded
    @POST("orden-consultar-tienda-entregada.php")
    fun consultarOrdenTiendaEntregada(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>

    /**
     * Consultar ordenes de una tienda (para Vendedor) que esten listas
     */
    @FormUrlEncoded
    @POST("orden-consultar-tienda-lista.php")
    fun consultarOrdenTiendaLista(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>

    /**
     * Consultar ordenes de una tienda (para Vendedor) que esten pendientes
     */
    @FormUrlEncoded
    @POST("orden-consultar-tienda-pendiente.php")
    fun consultarOrdenTiendaPendiente(
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