package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.repositorio.RepositorioOrden
import com.fao.orderfy.datos.utils.MainListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelOrden(application: Application): AndroidViewModel(application) {
    private val repositorio: RepositorioOrden
    init {
        val dao = BD.getDatabase(application).DaoOrden()
        repositorio = RepositorioOrden(dao)
    }

    fun consultarOrdenCliente(listener: MainListener, cliente: Cliente) {
        if(RetrofitService.isServerReachable() != false){
            repositorio.consultarOrdenClienteRemoto(listener, cliente)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.consultarOrdenClienteLocal(listener, cliente)
            }
        }
    }

    fun consultarOrdenTiendaLista(listener: MainListener, tienda: Tienda) {
        repositorio.consultarOrdenTiendaListaRemoto(listener, tienda)
    }

    fun consultarOrdenTiendaEntregada(listener: MainListener, tienda: Tienda) {
        repositorio.consultarOrdenTiendaEntregadaRemoto(listener, tienda)
    }

    fun consultarOrdenTiendaPendiente(listener: MainListener, tienda: Tienda) {
        repositorio.consultarOrdenTiendaPendienteRemoto(listener, tienda)
    }

    fun cambiarEstadoOrdenLista(listener: MainListener, orden: Orden) {
        repositorio.cambiarEstadoOrdenListaRemoto(listener, orden)
    }

    fun cambiarEstadoOrdenReclamada(listener: MainListener, orden: Orden) {
        repositorio.cambiarEstadoOrdenReclamadaRemoto(listener, orden)
    }

    fun insertarOrden(listener: MainListener, orden: Orden) {
        repositorio.insertarOrdenRemoto(listener, orden)
    }
}