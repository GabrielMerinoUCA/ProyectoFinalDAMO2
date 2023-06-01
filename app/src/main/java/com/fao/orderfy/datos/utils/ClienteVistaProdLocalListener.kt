package com.fao.orderfy.datos.utils

import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda

interface ClienteVistaProdLocalListener {
    fun onSelectItemClick(producto: Producto)
}