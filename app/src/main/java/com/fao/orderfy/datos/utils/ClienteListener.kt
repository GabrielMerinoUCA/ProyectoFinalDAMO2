package com.fao.orderfy.datos.utils

import com.fao.orderfy.datos.Entidades.Cliente

interface ClienteListener {
    fun onDeleteItem(cliente:Cliente)

}