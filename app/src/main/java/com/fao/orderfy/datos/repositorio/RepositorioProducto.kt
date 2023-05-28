package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.dao.DaoProducto
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiProducto
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import com.google.gson.Gson
import com.google.gson.JsonParser

class RepositorioProducto(val daoProducto: DaoProducto) {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarProductoRemoto(listener: MainListener, idTienda: Int) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.consultarProductosTienda(idTienda)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun editarProductoRemoto(listener: MainListener, producto: Producto) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.editarProducto(
            producto.idProducto,
            producto.nombre,
            producto.descripcion,
            producto.precio,
            producto.imagen,
            producto.disponibilidad,
            producto.tiempoEstimado
        )
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun eliminarProductoRemoto(listener: MainListener, producto: Producto) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.eliminarProducto(producto.idProducto)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun insertarProductoRemoto(listener: MainListener, producto: Producto) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.insertarProducto(
            producto.idTienda,
            producto.nombre,
            producto.descripcion,
            producto.precio,
            producto.imagen,
            producto.tiempoEstimado
        )
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun cambiarDisponibilidadProductoRemoto(listener: MainListener, producto: Producto) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.cambiarDisponibilidadProducto(producto.idProducto)
        requestMethods.request(service, listener)
    }

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////

    suspend fun consultarProductoLocal(listener: MainListener, idTienda: Int) {
        try {
            val gson = Gson()
            val producto = daoProducto.consultarProductosTienda(idTienda)
            val jsonString = gson.toJson(producto)
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