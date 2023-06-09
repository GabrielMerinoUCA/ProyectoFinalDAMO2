package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.repositorio.RepositorioProducto
import com.fao.orderfy.datos.utils.MainListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelProducto(application: Application): AndroidViewModel(application) {
    private val repositorio: RepositorioProducto
    init {
        val dao = BD.getDatabase(application).DaoProducto()
        repositorio = RepositorioProducto(dao)
    }

    fun consultarProducto(context: Context, listener: MainListener, idTienda: Int) {
        if(RetrofitService.isServerReachable(context) != false) {
            repositorio.consultarProductoRemoto(listener, idTienda)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.consultarProductoLocal(listener, idTienda)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editarProducto(listener: MainListener, producto: Producto) {
        repositorio.editarProductoRemoto(listener, producto)
    }

    fun eliminarProducto(listener: MainListener, producto: Producto) {
        repositorio.eliminarProductoRemoto(listener, producto)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertarProducto(listener: MainListener, producto: Producto) {
        repositorio.insertarProductoRemoto(listener, producto)
    }

    fun cambiarDisponibilidadProducto(listener: MainListener, producto: Producto) {
        repositorio.cambiarDisponibilidadProductoRemoto(listener, producto)
    }
}