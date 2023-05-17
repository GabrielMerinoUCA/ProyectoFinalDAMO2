package com.fao.orderfy.datos.repositorio

import android.annotation.SuppressLint
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiTienda
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import java.text.SimpleDateFormat

class RepositorioTienda {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarTiendaRemoto(listener: MainListener) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val service = api.consultarTienda()
        requestMethods.request(service, listener)
    }

    @SuppressLint("SimpleDateFormat")
    fun editarTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val horaApertura = SimpleDateFormat("HH:mm:ss").format(tienda.horaApertura)
        val horaCierre = SimpleDateFormat("HH:mm:ss").format(tienda.horaCierre)
        val service = api.editarTienda(tienda.idTienda, tienda.nombre, horaApertura, horaCierre, tienda.logo)
        requestMethods.request(service, listener)
    }

    fun eliminarTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val service = api.eliminarTienda(tienda.idTienda)
        requestMethods.request(service, listener)
    }

    fun cambiarEstadoTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val service = api.cambiarEstadoTienda(tienda.idTienda)
        requestMethods.request(service, listener)
    }

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////
}