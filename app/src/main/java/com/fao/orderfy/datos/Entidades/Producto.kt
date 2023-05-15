package com.fao.orderfy.datos.Entidades

import  android.graphics.drawable.Drawable
import java.time.LocalTime

class Producto(
    var idProducto: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Double,
    var disponibilidad: Boolean,
    var tiempoEstimado: LocalTime,
    var imagen: ByteArray,
    var idTienda: Int,
)