package com.fao.orderfy.datos.remoto.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress


object RetrofitService {
    private val api=
        Retrofit.Builder()
            .baseUrl("https://damo2-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun <T>getApi(service: Class<T>): T {
        return api.create(service)
    }

    fun isServerReachable(): Boolean {
        return try {
            val direccion = InetAddress.getByName("https://damo2-api.herokuapp.com/")
            direccion.isReachable(5000)
        } catch (e: Exception) {
            false
        }
    }
}