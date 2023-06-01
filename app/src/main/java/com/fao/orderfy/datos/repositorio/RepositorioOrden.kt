package com.fao.orderfy.datos.repositorio

import android.annotation.SuppressLint
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.dao.DaoOrden
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiOrden
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.text.SimpleDateFormat

class RepositorioOrden(val daoOrden: DaoOrden) {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarOrdenClienteRemoto(listener: MainListener, cliente: Cliente) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenCliente(cliente.idCliente)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun consultarOrdenTiendaEntregadaRemoto(listener: MainListener, idTienda: Int) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenTiendaEntregada(idTienda)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun consultarOrdenTiendaListaRemoto(listener: MainListener, idTienda: Int) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenTiendaLista(idTienda)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun consultarOrdenTiendaPendienteRemoto(listener: MainListener, idTienda: Int) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.consultarOrdenTiendaPendiente(idTienda)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun cambiarEstadoOrdenListaRemoto(listener: MainListener, orden: Orden) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val service = api.cambiarEstadoOrdenLista(orden.idOrden)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    @SuppressLint("SimpleDateFormat")
    fun cambiarEstadoOrdenReclamadaRemoto(listener: MainListener, orden: Orden) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val horaReclamo = SimpleDateFormat("HH:mm:ss").format(orden.horaReclamo)
        val service = api.cambiarEstadoOrdenReclamada(orden.idOrden, horaReclamo)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    @SuppressLint("SimpleDateFormat")
    fun insertarOrdenRemoto(listener: MainListener, orden: Orden) {
        val api: ApiOrden = RetrofitService.getApi(ApiOrden::class.java)
        val horaPedido = SimpleDateFormat("HH:mm:ss").format(orden.horaPedido)
        val service =
            api.insertarOrden(horaPedido, orden.idCliente, orden.idProducto, orden.cantidad)
        requestMethods.request(service, listener)
    }


    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////

    suspend fun consultarOrdenClienteLocal(listener: MainListener, cliente: Cliente) {
        try {
            val gson = Gson()
            val orden = daoOrden.consultarOrdenCliente(cliente.idCliente)
            val jsonString = gson.toJson(orden)
            val jsonParser = JsonParser()
            val jsonArray = jsonParser.parse(jsonString).asJsonArray
            if (jsonArray != null){
                listener.onSuccess(jsonArray)
            }else{
                listener.onFailure("No se a encontrado el registro!")
            }
        }catch (e: Exception){
            listener.onFailure("Error inesperado...")
            e.printStackTrace()
        }
    }

}