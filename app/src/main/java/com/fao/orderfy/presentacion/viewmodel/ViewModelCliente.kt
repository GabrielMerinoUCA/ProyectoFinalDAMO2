package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.repositorio.RepositorioCliente
import com.fao.orderfy.datos.utils.MainListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelCliente(application: Application): AndroidViewModel(application) {
    private val repositorio: RepositorioCliente
    init {
        val dao = BD.getDatabase(application).DaoCliente()
        repositorio = RepositorioCliente(dao)
    }

    fun consultarClienteNombreUsuario(listener: MainListener, nombreUsuario: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.consultarClienteNombreUsuarioLocal(listener, nombreUsuario)
        }
    }

    fun autenticarCliente(context: Context,listener: MainListener, cliente: Cliente) {
        if (RetrofitService.isServerReachable(context)) {
            repositorio.autenticarClienteRemoto(listener, cliente)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.autenticarClienteLocal(listener, cliente)
            }
        }
    }

    fun consultarCliente(listener: MainListener) {
        repositorio.consultarClienteRemoto(listener)
    }

    fun editarCliente(listener: MainListener, cliente: Cliente) {
        repositorio.editarClienteRemoto(listener, cliente)
    }

    fun eliminarCliente(listener: MainListener, cliente: Cliente) {
        repositorio.eliminarClienteRemoto(listener, cliente)
    }

    fun registrarCliente(listener: MainListener, cliente: Cliente) {
        repositorio.registrarClienteRemoto(listener, cliente)
    }
}