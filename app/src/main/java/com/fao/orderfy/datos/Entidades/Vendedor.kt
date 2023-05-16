package com.fao.orderfy.datos.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "vendedor",
    foreignKeys = [
        ForeignKey(entity = Tienda::class,
            parentColumns = ["idTienda"],
            childColumns = ["idTienda"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class Vendedor(
    var idTienda: Int,

    @PrimaryKey(autoGenerate = false)
    var idVendor: Int,

    @ColumnInfo(name = "nombreUsuario")
    var nombreUsuario: String,

    @ColumnInfo(name = "pwd")
    var pwd: String,
)
