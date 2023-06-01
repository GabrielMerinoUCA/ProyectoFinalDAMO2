package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.repositorio.RepositorioTienda
import com.fao.orderfy.datos.utils.MainListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelTienda(application: Application): AndroidViewModel(application) {
    private val repositorio: RepositorioTienda
    init {
        val dao = BD.getDatabase(application).DaoTienda()
        repositorio = RepositorioTienda(dao)
    }

    fun consultarTienda(context: Context, listener: MainListener) {
        if(RetrofitService.isServerReachable(context) != false) {
            repositorio.consultarTiendaRemoto(listener)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.consultarTiendaLocal(listener)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editarTienda(listener: MainListener, tienda: Tienda) {
        repositorio.editarTiendaRemoto(listener, tienda)
    }

    fun eliminarTienda(listener: MainListener, tienda: Tienda) {
        repositorio.eliminarTiendaRemoto(listener, tienda)
    }

    fun cambiarEstadoTienda(listener: MainListener, idTienda: Int) {
        repositorio.cambiarEstadoTiendaRemoto(listener, idTienda)
    }
}