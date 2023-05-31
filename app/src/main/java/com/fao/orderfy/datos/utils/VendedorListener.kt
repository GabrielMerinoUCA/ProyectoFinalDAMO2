package com.fao.orderfy.datos.utils

import com.fao.orderfy.datos.Entidades.Vendedor

interface VendedorListener {
    fun onDeleteVendedor(vendedor: Vendedor)
}