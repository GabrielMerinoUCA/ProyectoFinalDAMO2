package com.fao.orderfy.datos.Entidades

import android.media.Image
import java.sql.Time
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tienda")
data class Tienda(
    @PrimaryKey(autoGenerate = false)
    var idTienda: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "logo")
    var logo: ByteArray,

    @ColumnInfo(name = "horaApertura")
    var horaApertura: Time,

    @ColumnInfo(name = "horaCierre")
    var horaCierre: Time,

    @ColumnInfo(name = "estado")
    var estado: Boolean,
)
