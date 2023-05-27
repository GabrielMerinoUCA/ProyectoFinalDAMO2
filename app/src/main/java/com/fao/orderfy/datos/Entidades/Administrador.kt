package com.fao.orderfy.datos.Entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "administrador")
data class Administrador(
    @PrimaryKey(autoGenerate = false)
    var idAdministrador: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "apellido")
    var apellido: String,
    @ColumnInfo(name = "nombreUsuario")
    var nombreUsuario: String,
    @ColumnInfo(name = "pwd")
    var pwd: String
) : Parcelable