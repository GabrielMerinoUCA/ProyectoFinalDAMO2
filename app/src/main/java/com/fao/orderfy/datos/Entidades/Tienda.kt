package com.fao.orderfy.datos.Entidades

import android.media.Image
import java.sql.Time

class Tienda(
    var estado: Boolean,
    var horaApertura: Time,
    var horaCierre: Time,
    var idTienda: Int,
    var logo: ByteArray,
    var nombre: String,
)

