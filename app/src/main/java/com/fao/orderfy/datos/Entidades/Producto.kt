package com.fao.orderfy.datos.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "producto",
    foreignKeys = [
        ForeignKey(
            entity = Tienda::class,
            parentColumns = ["idTienda"],
            childColumns = ["idTienda"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Producto(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idProducto")
    var idProducto: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "precio")
    var precio: Double,

    @ColumnInfo(name = "imagen")
    var imagen: ByteArray,

    @ColumnInfo(name = "descripcion")
    var descripcion: String,

    @ColumnInfo(name = "disponibilidad")
    var disponibilidad: Boolean,

    @ColumnInfo(name = "tiempoEstimado")
    var tiempoEstimado: Long,

    var idTienda: Int
)
