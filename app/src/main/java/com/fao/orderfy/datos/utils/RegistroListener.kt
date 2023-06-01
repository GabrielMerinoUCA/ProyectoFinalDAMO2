package com.fao.orderfy.datos.utils

import com.fao.orderfy.datos.Entidades.Registro

interface RegistroListener {
    fun onSelectedItem(registro: Registro)
}