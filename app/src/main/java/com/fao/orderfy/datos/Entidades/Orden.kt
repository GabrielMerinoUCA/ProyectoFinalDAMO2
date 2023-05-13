package com.fao.orderfy.datos.Entidades

import java.sql.Time
import java.time.LocalTime

class Orden(
    var estado: Int,
    var horaPedido: Time,
    var horaReclamo: Time,
    var idCliente: Int,
    var idOrden: Int,
    var idProducto: Int,
    var cantidad: Int,
)
