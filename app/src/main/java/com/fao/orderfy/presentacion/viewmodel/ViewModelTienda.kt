package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
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

    fun consultarTienda(listener: MainListener) {
        if(RetrofitService.isServerReachable() != false) {
            repositorio.consultarTiendaRemoto(listener)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.consultarTiendaLocal(listener)
            }
        }
    }

    fun editarTienda(listener: MainListener, tienda: Tienda) {
        repositorio.editarTiendaRemoto(listener, tienda)
    }

    fun eliminarTienda(listener: MainListener, tienda: Tienda) {
        repositorio.eliminarTiendaRemoto(listener, tienda)
    }

    fun cambiarEstadoTienda(listener: MainListener, tienda: Tienda) {
        repositorio.cambiarEstadoTiendaRemoto(listener, tienda)
    }
}