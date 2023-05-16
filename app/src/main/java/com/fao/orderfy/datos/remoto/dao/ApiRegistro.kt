package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRegistro {
    /**
     * Consultar lista de peticiones de registracion de vendedores
     */
    @GET("registro-consultar.php")
    fun consultarRegistro(): Call<JsonArray>

    /**
     * Insertar solicitud de registracion de un vendedor
     */
    @FormUrlEncoded
    @POST("registro-insertar.php")
    fun insertarRegistro(
        @Field("nombre") nombre: String,
        @Field("apellido") apellido: String,
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("nombreTienda") nombreTienda: String,
        @Field("horaApertura") horaApertura: String,
        @Field("horaCierre") horaCierre: String,
        @Field("pwd") pwd: String,
    ): Call<JsonArray>

    /**
     * Aceptar solicitud de registracion de un vendedor
     */
    @FormUrlEncoded
    @POST("registro-aceptar.php")
    fun aceptarRegistro(
        @Field("idRegistro") idRegistro: Int
    ): Call<JsonArray>

    /**
     * Aceptar solicitud de registracion de un vendedor. Cuando se acepte, se creara autimaticamente
     * la tienda,
     */
    @FormUrlEncoded
    @POST("registro-rechazar.php")
    fun rechazarRegistro(
        @Field("idRegistro") idRegistro: Int
    ): Call<JsonArray>
}