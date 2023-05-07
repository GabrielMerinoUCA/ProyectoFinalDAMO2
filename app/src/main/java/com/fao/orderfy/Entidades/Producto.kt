package com.fao.orderfy.Entidades

import android.widget.ImageView
import java.time.LocalTime

class Producto(
    var idProducto: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Double,
    var disponibilidad: Boolean,
    var tiempoEstimado: LocalTime,
    var imagen: ImageView,
    var idTienda: Int

)