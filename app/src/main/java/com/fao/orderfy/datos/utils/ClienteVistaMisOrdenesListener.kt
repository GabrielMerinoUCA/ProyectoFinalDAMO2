package com.fao.orderfy.datos.utils

import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda

interface ClienteVistaMisOrdenesListener {
    fun onSelectItemClick(orden: Orden, producto: Producto, tienda: Tienda)

}