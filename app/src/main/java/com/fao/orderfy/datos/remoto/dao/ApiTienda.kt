package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiTienda {
    /**
     * Consultar todos los registros de tiendas
     */
    @GET("tienda-consultar.php")
    fun consultarTienda(): Call<JsonArray>

    /**
     * Editar tienda en especifico
     */
    @FormUrlEncoded
    @POST("tienda-editar.php")
    fun editarTienda(
        @Field("idTienda") idTienda: Int,
        @Field("nombre") nombre: String,
        @Field("horaApertura") horaApertura: String,
        @Field("horaCierre") horaCierre: String,
        @Field("logo") logo: ByteArray
    ): Call<JsonArray>

    /**
     * Eliminar tienda del registro
     */
    @FormUrlEncoded
    @POST("tienda-eliminar.php")
    fun eliminarTienda(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>

    /**
     * Cambiar estado actual de la tienda al estado opuesto
     */
    @FormUrlEncoded
    @POST("tienda-cambiar-estado.php")
    fun cambiarEstadoTienda(
        @Field("idTienda") idTienda: Int
    ): Call<JsonArray>


}