package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import android.content.Context
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

    fun consultarOrdenCliente(context: Context, listener: MainListener, cliente: Cliente) {
        if(RetrofitService.isServerReachable(context) != false){
            repositorio.consultarOrdenClienteRemoto(listener, cliente)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.consultarOrdenClienteLocal(listener, cliente)
            }
        }
    }

    fun consultarOrdenTiendaLista(listener: MainListener, idTienda: Int) {
        repositorio.consultarOrdenTiendaListaRemoto(listener, idTienda)
    }

    fun consultarOrdenTiendaEntregada(listener: MainListener, idTienda: Int) {
        repositorio.consultarOrdenTiendaEntregadaRemoto(listener, idTienda)
    }

    fun consultarOrdenTiendaPendiente(listener: MainListener, idTienda: Int) {
        repositorio.consultarOrdenTiendaPendienteRemoto(listener, idTienda)
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