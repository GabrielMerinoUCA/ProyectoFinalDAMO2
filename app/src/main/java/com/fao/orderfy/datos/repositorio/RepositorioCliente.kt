package com.fao.orderfy.datos.repositorio

import android.util.Log
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.local.dao.DaoCliente
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiCliente
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import com.google.gson.Gson
import com.google.gson.JsonParser

class RepositorioCliente(val daoCliente: DaoCliente) {
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

    suspend fun consultarClienteNombreUsuarioLocal(listener: MainListener, nombreUsuario: String) {
        try {
            val gson = Gson()
            val cliente = daoCliente.getClienteByNombreUsuario(nombreUsuario)
            val jsonString = gson.toJson(cliente)
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

    suspend fun autenticarClienteLocal(listener: MainListener, cliente: Cliente) {
        try{
            val gson = Gson()
            val estadoAutenticacion = daoCliente.authenticateCliente(cliente.nombreUsuario, cliente.pwd)
            val jsonString = gson.toJson(estadoAutenticacion)
            val jsonParser = JsonParser()
            val jsonArray = jsonParser.parse(jsonString).asJsonArray
            if(jsonArray != null) {
                listener.onSuccess(jsonArray)
            }else{
                listener.onFailure("Credenciales incorrectas")
            }
        }catch (e: Exception){
            Log.wtf("error", "error: "+e)
            listener.onFailure("Error inesperado...")
            e.printStackTrace()
        }
    }
}