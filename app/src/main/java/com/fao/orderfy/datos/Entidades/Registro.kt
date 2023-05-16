package com.fao.orderfy.datos.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "registro")
data class Registro(
    @PrimaryKey(autoGenerate = false)
    var idRegistro: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "apellido")
    var apellido: String,

    @ColumnInfo(name = "nombreUsuario")
    var nombreUsuario: String,

    @ColumnInfo(name = "nombreTienda")
    var nombreTienda: String,

    @ColumnInfo(name = "pwd")
    var pwd: String,

    @ColumnInfo(name = "horaApertura")
    var horaApertura: Time,

    @ColumnInfo(name = "horaCierre")
    var horaCierre: Time,

    @ColumnInfo(name = "estado")
    var estado: Int
)
