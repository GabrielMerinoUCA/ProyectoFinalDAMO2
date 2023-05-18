package com.fao.orderfy.datos.remoto.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    private val api=
        Retrofit.Builder()
            .baseUrl("https://damo2-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun <T>getApi(service: Class<T>): T {
        return api.create(service)
    }
}