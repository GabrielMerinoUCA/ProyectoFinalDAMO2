package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiCliente
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods

class RepositorioCliente {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarClienteRemoto(listener: MainListener) {
        val api: ApiCliente = RetrofitService.getApi(ApiCliente::class.java)
        val service = api.consultarCliente()
        requestMethods.request(service, listener)
    }

    fun editarClienteRemoto(listener: MainListener, cliente: Cliente) {
        val api: ApiCliente = RetrofitService.getApi(ApiCliente::class.java)
        val service = api.editarCliente(
            cliente.idCliente,
            cliente.nombre,
            cliente.apellido,
            cliente.nombre,
            cliente.pwd
        )
        requestMethods.request(service, listener)
    }

    fun eliminarClienteRemoto(listener: MainListener, cliente: Cliente) {
        val api: ApiCliente = RetrofitService.getApi(ApiCliente::class.java)
        val service = api.eliminarCliente(cliente.idCliente)
        requestMethods.request(service, listener)
    }

    fun registrarClienteRemoto(listener: MainListener, cliente: Cliente) {
        val api: ApiCliente = RetrofitService.getApi(ApiCliente::class.java)
        val service = api.registrarCliente(
            cliente.nombre,
            cliente.apellido,
            cliente.nombreUsuario,
            cliente.pwd
        )
        requestMethods.request(service, listener)
    }

    fun autenticarClienteRemoto(listener: MainListener, cliente: Cliente) {
        val api: ApiCliente = RetrofitService.getApi(ApiCliente::class.java)
        val service = api.autenticarCliente(cliente.nombreUsuario, cliente.pwd)
        requestMethods.request(service, listener)
    }

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////


}