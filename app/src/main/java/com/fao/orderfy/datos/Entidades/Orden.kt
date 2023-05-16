package com.fao.orderfy.datos.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(
    tableName = "orden",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["idCliente"],
            childColumns = ["idCliente"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["idProducto"],
            childColumns = ["idProducto"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Orden(
    @PrimaryKey(autoGenerate = false)
    var idOrden: Int,
    @ColumnInfo(name = "horaPedido")
    var horaPedido: Time,
    @ColumnInfo(name = "horaReclamo")
    var horaReclamo: Time,
    @ColumnInfo(name = "cantidad")
    var cantidad: Int,
    @ColumnInfo(name = "estado")
    var estado: Int,
    var idCliente: Int,
    var idProducto: Int,
)
