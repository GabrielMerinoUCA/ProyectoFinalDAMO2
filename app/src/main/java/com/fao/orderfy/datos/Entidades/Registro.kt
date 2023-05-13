package com.fao.orderfy.datos.Entidades

import java.sql.Time

class Registro(
    var apellido: String,
    var estado: Int,
    var horaApertura: Time,
    var horaCierra: Time,
    var idRegistro: Int,
    var nombre: String,
    var nombreTienda: String,
    var nombreUsuario: String,
    var pwd: String,
)