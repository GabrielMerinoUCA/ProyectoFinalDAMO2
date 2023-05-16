package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiAdministrador {
    /**
     * Consultar registro de administradores
     */
    @GET("admin-consultar.php")
    fun consultarAdministrador(): Call<JsonArray>

    /**
     * Editar Administrador especifico del registro
     */
    @FormUrlEncoded
    @POST("admin-editar.php")
    fun editarAdministrador(
        @Field("idAdministrador") idAdministrador: Int,
        @Field("nombre") nombre: String,
        @Field("apellido") apellido: String,
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd: String
    ): Call<JsonArray>

    /**
     * Autenticar usuario de tipo administrador al sistema
     */
    @FormUrlEncoded
    @POST("admin-autenticacion.php")
    fun autenticarAdministrador(
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd: String
    ): Call<JsonArray>
}