package com.fao.orderfy.datos.remoto.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.Retrofit
import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL


object RetrofitService {
    private val api=
        Retrofit.Builder()
            .baseUrl("https://damo2-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun <T>getApi(service: Class<T>): T {
        return api.create(service)
    }

    fun isServerReachable(contexto: Context): Boolean {
        val gestorConectividad = contexto.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val red = gestorConectividad.activeNetwork
        val capacidadRed = gestorConectividad.getNetworkCapabilities(red)
        return capacidadRed?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true && capacidadRed.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}