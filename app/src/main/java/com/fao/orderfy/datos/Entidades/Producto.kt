package com.fao.orderfy.datos.Entidades

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import java.time.LocalTime

class Producto(
    var idProducto: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Double,
    var disponibilidad: Boolean,
    var tiempoEstimado: LocalTime,
    var imagen: Drawable,
    var idTienda: Int

)