package com.fao.orderfy.presentacion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.repositorio.RepositorioRegistro
import com.fao.orderfy.datos.utils.MainListener

class ViewModelRegistro(application: Application): AndroidViewModel(application) {
    private val repositorio = RepositorioRegistro()

    fun consultarRegistro(listener: MainListener) {
        repositorio.consultarRegistroRemoto(listener)
    }

    fun insertarRegistro(listener: MainListener, registro: Registro) {
        repositorio.insertarRegistroRemoto(listener, registro)
    }

    fun aceptarRegistro(listener: MainListener, registro: Registro) {
        repositorio.aceptarRegistroRemoto(listener, registro)
    }

    fun rechazarRegistro(listener: MainListener, registro: Registro) {
        repositorio.rechazarRegistroRemoto(listener, registro)
    }

}