package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiVendedor {
    /**
     * Autenticar credenciales de usuario vendedor
     */
    @FormUrlEncoded
    @POST("vendedor-autenticacion.php")
    fun autenticarVendedor(
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd:String
    ):Call<JsonArray>

    /**
     * Consultar registro de vendedores
     */
    @GET("vendedor-consultar.php")
    fun consultarVendedor(): Call<JsonArray>

    /**
     * Editar registro de vendedor especifico
     */
    @FormUrlEncoded
    @POST("vendedor-editar.php")
    fun editarVendedor(
        @Field("idVendedor") idVendedor: Int,
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd: String
    ): Call<JsonArray>

    /**
     * Eliminar Vendedor del registro
     */
    @FormUrlEncoded
    @POST("vendedor-eliminar.php")
    fun eliminarVendedor(
        @Field("idVendedor") idVendedor: Int
    ): Call<JsonArray>


}