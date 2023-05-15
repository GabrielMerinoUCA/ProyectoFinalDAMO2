package com.fao.orderfy.datos.remoto.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    private val api=
        Retrofit.Builder()
            .baseUrl("http://192.168.1.23/DAMO2Final/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun <T>getApi(service: Class<T>): T {
        return api.create(service)
    }
}