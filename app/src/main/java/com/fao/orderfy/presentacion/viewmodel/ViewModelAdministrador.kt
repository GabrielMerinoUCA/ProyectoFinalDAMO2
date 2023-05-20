package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fao.orderfy.datos.Entidades.Administrador
import com.fao.orderfy.datos.repositorio.RepositorioAdministrador
import com.fao.orderfy.datos.utils.MainListener

class ViewModelAdministrador(application: Application): AndroidViewModel(application) {
    private val repositorio = RepositorioAdministrador()

    fun editarAdministrador(listener: MainListener, administrador: Administrador) {
        repositorio.editarAdministradorRemoto(listener, administrador)
    }
}