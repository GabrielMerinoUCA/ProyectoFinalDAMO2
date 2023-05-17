package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiVendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods

class RepositorioVendedor {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarVendedorRemoto(listener: MainListener) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.consultarVendedor()
        requestMethods.request(service, listener)
    }

    fun editarVendedorRemoto(listener: MainListener, vendedor: Vendedor) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.editarVendedor(vendedor.idVendor, vendedor.nombreUsuario, vendedor.pwd)
        requestMethods.request(service, listener)
    }

    fun eliminarVendedorRemoto(listener: MainListener, vendedor: Vendedor) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.eliminarVendedor(vendedor.idVendor)
        requestMethods.request(service, listener)
    }

    // Tambien autentica el administrador pero eso es un secreto 🤫🤫🤫
    fun autenticarVendedorRemoto(listener: MainListener, usuario: Vendedor) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.autenticarVendedor(usuario.nombreUsuario, usuario.pwd)
        requestMethods.request(service, listener)
    }

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////

    /* ESTA SECCION NO ES PARA QUE EL VENDEDOR ACCEDA DE MANERA LOCAL, ES PARA CARGAR DATOS DE LA TIENDA DEL CLIENTE*/
}