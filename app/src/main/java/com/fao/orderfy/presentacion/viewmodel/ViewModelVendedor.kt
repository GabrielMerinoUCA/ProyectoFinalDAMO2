package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.repositorio.RepositorioVendedor
import com.fao.orderfy.datos.utils.MainListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelVendedor(application: Application): AndroidViewModel(application) {
    private val repositorio: RepositorioVendedor
    init {
        val dao = BD.getDatabase(application).DaoVendedor()
        repositorio = RepositorioVendedor(dao)
    }

    fun consultarVendedor(context: Context, listener: MainListener) {
        if(RetrofitService.isServerReachable(context) != false) {
            repositorio.consultarVendedorRemoto(listener)
        }else {
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.consultarVendedorLocal(listener)
            }
        }
    }

    fun editarVendedor(listener: MainListener, vendedor: Vendedor) {
        repositorio.editarVendedorRemoto(listener, vendedor)
    }

    fun eliminarVendedor(listener: MainListener, vendedor: Vendedor) {
        repositorio.eliminarVendedorRemoto(listener, vendedor)
    }

    fun autenticarVendedor(listener: MainListener, vendedor: Vendedor) {
        repositorio.autenticarVendedorRemoto(listener, vendedor)
    }
}