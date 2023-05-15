package com.fao.orderfy.datos.remoto.dao

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiCliente {
    /**
     * Registrar nuevo cliente
     */
    @FormUrlEncoded
    @POST("cliente-registro.php")
    fun registrarCliente(
        @Field("nombre") nombre: String,
        @Field("apellido") apellido: String,
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd: String
    ): Call<JsonArray>

    /**
     * Eliminar Cliente del registro
     */
    @FormUrlEncoded
    @POST("cliente-eliminar.php")
    fun eliminarCliente(
        @Field("idCliente") idCliente: Int
    ): Call<JsonArray>

    /**
     * Editar cliente especifico
     */
    @FormUrlEncoded
    @POST("cliente-editar.php")
    fun editarCliente(
        @Field("idCliente") idCliente: Int,
        @Field("nombre") nombre: String,
        @Field("apellido") apellido: String,
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd: String
    ): Call<JsonArray>

    /**
     * Autenticar Cliente para inicio de sesion
     */
    @FormUrlEncoded
    @POST("cliente-autenticacion.php")
    fun autenticarCliente(
        @Field("nombreUsuario") nombreUsuario: String,
        @Field("pwd") pwd: String
    ): Call<JsonArray>

    //TODO(Hay que poner la funcion para consultar clientes)



}