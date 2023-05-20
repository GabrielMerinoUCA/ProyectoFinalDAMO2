package com.fao.orderfy.datos.repositorio

import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.dao.DaoVendedor
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiVendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.lang.Exception

class RepositorioVendedor(val daoVendedor: DaoVendedor) {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarVendedorRemoto(listener: MainListener) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.consultarVendedor()
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun editarVendedorRemoto(listener: MainListener, vendedor: Vendedor) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.editarVendedor(vendedor.idVendedor, vendedor.nombreUsuario, vendedor.pwd)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun eliminarVendedorRemoto(listener: MainListener, vendedor: Vendedor) {
        val api: ApiVendedor = RetrofitService.getApi(ApiVendedor::class.java)
        val service = api.eliminarVendedor(vendedor.idVendedor)
        requestMethods.request(service, listener)
    }
    // SOLO REMOTO
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
    suspend fun consultarVendedorLocal(listener: MainListener) {
        try {
            val gson = Gson()
            val vendedor = daoVendedor.obtenerVendedores()
            val jsonString = gson.toJson(vendedor)
            val jsonParser = JsonParser()
            val jsonArray = jsonParser.parse(jsonString).asJsonArray
            if(jsonArray != null) {
                listener.onSuccess(jsonArray)
            }else{
                listener.onFailure("No se encontro el registro!")
            }
        }catch (e: Exception) {
            listener.onFailure("Error inesperado...")
            e.printStackTrace()
        }
    }

}