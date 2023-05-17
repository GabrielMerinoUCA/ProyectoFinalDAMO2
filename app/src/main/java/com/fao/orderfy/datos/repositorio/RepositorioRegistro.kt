package com.fao.orderfy.datos.repositorio

import android.annotation.SuppressLint
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiRegistro
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import java.text.SimpleDateFormat

class RepositorioRegistro {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarRegistroRemoto(listener: MainListener) {
        val api: ApiRegistro = RetrofitService.getApi(ApiRegistro::class.java)
        val service = api.consultarRegistro()
        requestMethods.request(service, listener)
    }

    @SuppressLint("SimpleDateFormat")
    fun insertarRegistroRemoto(listener: MainListener, registro: Registro) {
        val api: ApiRegistro = RetrofitService.getApi(ApiRegistro::class.java)
        val horaApertura = SimpleDateFormat("HH:mm:ss").format(registro.horaApertura)
        val horaCierre = SimpleDateFormat("HH:mm:ss").format(registro.horaCierre)
        val service = api.insertarRegistro(
            registro.nombre,
            registro.apellido,
            registro.nombreUsuario,
            registro.nombreTienda,
            horaApertura,
            horaCierre,
            registro.pwd
        )
        requestMethods.request(service, listener)
    }

    fun aceptarRegistroRemoto(listener: MainListener, registro: Registro) {
        val api: ApiRegistro = RetrofitService.getApi(ApiRegistro::class.java)
        val service = api.aceptarRegistro(registro.idRegistro)
        requestMethods.request(service, listener)
    }

    fun rechazarRegistroRemoto(listener: MainListener, registro: Registro) {
        val api: ApiRegistro = RetrofitService.getApi(ApiRegistro::class.java)
        val service = api.rechazarRegistro(registro.idRegistro)
        requestMethods.request(service, listener)
    }

    // no lleva local
}