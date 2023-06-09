package com.fao.orderfy.datos.repositorio

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.dao.DaoTienda
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.remoto.dao.ApiTienda
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RequestMethods
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.text.SimpleDateFormat
import java.util.Base64

class RepositorioTienda(val daoTienda: DaoTienda) {
    private val requestMethods: RequestMethods = RequestMethods()

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO REMOTO   //////////
    //////////////////////////////////////////////////

    fun consultarTiendaRemoto(listener: MainListener) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val service = api.consultarTienda()
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun editarTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val horaApertura = SimpleDateFormat("HH:mm:ss").format(tienda.horaApertura)
        val horaCierre = SimpleDateFormat("HH:mm:ss").format(tienda.horaCierre)
        val service = api.editarTienda(tienda.idTienda, tienda.nombre, horaApertura, horaCierre, byteArrayToBase64(tienda.logo))
        requestMethods.request(service, listener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun byteArrayToBase64(byteArray: ByteArray): String {
        val base64Bytes = Base64.getEncoder().encode(byteArray)
        return String(base64Bytes)
    }
    // SOLO REMOTO
    fun eliminarTiendaRemoto(listener: MainListener, tienda: Tienda) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val service = api.eliminarTienda(tienda.idTienda)
        requestMethods.request(service, listener)
    }

    // SOLO REMOTO
    fun cambiarEstadoTiendaRemoto(listener: MainListener, idTienda: Int) {
        val api: ApiTienda = RetrofitService.getApi(ApiTienda::class.java)
        val service = api.cambiarEstadoTienda(idTienda)
        requestMethods.request(service, listener)
    }

    //////////////////////////////////////////////////
    //////////  SECCCION DE ACCESO LOCAL    //////////
    //////////////////////////////////////////////////

    suspend fun consultarTiendaLocal(listener: MainListener) {
        try {
            val gson = Gson()
            val tienda = daoTienda.consultarTienda()
            val jsonString = gson.toJson(tienda)
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