package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.Entidades.Administrador
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiAdministrador
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositorioAdministrador {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////
    fun consultarAdministradorRemoto(listener: MainListener) {
        val api: ApiAdministrador = RetrofitService.getApi(ApiAdministrador::class.java)
        val service = api.consultarAdministrador()
        requestMethods.request(service, listener)
    }

    fun editarAdministradorRemoto(listener: MainListener, administrador: Administrador){
        val api: ApiAdministrador = RetrofitService.getApi(ApiAdministrador::class.java)
        val service = api.editarAdministrador(administrador)
        requestMethods.request(service, listener)
    }


}