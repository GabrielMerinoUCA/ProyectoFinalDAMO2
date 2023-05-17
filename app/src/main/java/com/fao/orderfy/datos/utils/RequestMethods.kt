package com.fao.orderfy.datos.utils

import android.util.Log
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestMethods {
    fun request(service: Call<JsonArray>, listener: MainListener){
        service.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        listener.onSuccess(body)
                        Log.w("RequestMethods: ", "Todo en orden")
                    }else{
                        listener.onFailure("No se retorno nada!")
                    }
                }else{
                    listener.onFailure("Error en la respuesta!")
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                listener.onFailure("Error al procesar respuesta o crear peticion")
                t.printStackTrace()
            }
        })
    }
}