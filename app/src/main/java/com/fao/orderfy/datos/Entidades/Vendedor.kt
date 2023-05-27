package com.fao.orderfy.datos.Entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var idVendedor: Int,

    @ColumnInfo(name = "nombreUsuario")
    var nombreUsuario: String,

    @ColumnInfo(name = "pwd")
    var pwd: String,
): Parcelable
