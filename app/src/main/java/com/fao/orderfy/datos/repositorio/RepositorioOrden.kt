package com.fao.orderfy.datos.repositorio

import android.annotation.SuppressLint
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiOrden
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import java.text.SimpleDateFormat

class RepositorioOrden {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarOrdenClienteRemoto(listener: MainListener, cliente: Cliente) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenCliente(cliente.idCliente)
        requestMethods.request(service, listener)
    }

    fun consultarOrdenPendienteTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenPendienteTienda(tienda.idTienda)
        requestMethods.request(service, listener)
    }

    fun consultarOrdenTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenTienda(tienda.idTienda)
        requestMethods.request(service, listener)
    }

    fun cambiarEstadoOrdenListaRemoto(listener: MainListener, orden: Orden) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.cambiarEstadoOrdenLista(orden.idOrden)
        requestMethods.request(service, listener)
    }

    @SuppressLint("SimpleDateFormat")
    fun cambiarEstadoOrdenReclamadaRemoto(listener: MainListener, orden: Orden) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val horaReclamo = SimpleDateFormat("HH:mm:ss").format(orden.horaReclamo)
        val service = api.cambiarEstadoOrdenReclamada(orden.idOrden, horaReclamo)
        requestMethods.request(service, listener)
    }

    @SuppressLint("SimpleDateFormat")
    fun insertarOrdenRemoto(listener: MainListener, orden: Orden) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val horaPedido = SimpleDateFormat("HH:mm:ss").format(orden.horaPedido)
        val service = api.insertarOrden(horaPedido, orden.idCliente, orden.idProducto, orden.cantidad)
        requestMethods.request(service, listener)
    }


    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////
}