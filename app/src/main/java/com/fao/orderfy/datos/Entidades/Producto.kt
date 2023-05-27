package com.fao.orderfy.datos.Entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var precio: Float,

    @ColumnInfo(name = "imagen")
    var imagen: ByteArray,

    @ColumnInfo(name = "descripcion")
    var descripcion: String,

    @ColumnInfo(name = "disponibilidad")
    var disponibilidad: Boolean,

    @ColumnInfo(name = "tiempoEstimado")
    var tiempoEstimado: Int,

    var idTienda: Int
): Parcelable
