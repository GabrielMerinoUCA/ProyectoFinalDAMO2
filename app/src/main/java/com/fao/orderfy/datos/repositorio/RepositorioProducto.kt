package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiProducto
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods

class RepositorioProducto {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarProductoRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.consultarProductosTienda(tienda.idTienda)
        requestMethods.request(service, listener)
    }

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

    fun eliminarProductoRemoto(listener: MainListener, producto: Producto) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.eliminarProducto(producto.idProducto)
        requestMethods.request(service, listener)
    }

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

    fun cambiarDisponibilidadProductoRemoto(listener: MainListener, producto: Producto) {
        val api: ApiProducto = RetrofitService.getApi(ApiProducto::class.java)
        val service = api.cambiarDisponibilidadProducto(producto.idProducto)
        requestMethods.request(service, listener)
    }

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////

}