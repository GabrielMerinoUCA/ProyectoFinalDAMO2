package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiAdministrador
import com.fao.orderfy.datos.utils.MainListener
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositorioAdministrador {
    fun consultarAdministradorRemoto(listener: MainListener) {
        val api: ApiAdministrador = RetrofitService.getApi(ApiAdministrador::class.java)
        val service = api.consultarAdministrador()
        service.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        listener.onSuccess(body)
                    } else {
                        listener.onFailure("Cuerpo Nulo!")
                    }
                } else {
                    listener.onFailure("Respuesta no fue exitosa!")
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                listener.onFailure("Error de conexion, al crear la solicitud, o al procesar la respuesta")
                t.printStackTrace()
            }
        })
    }
}