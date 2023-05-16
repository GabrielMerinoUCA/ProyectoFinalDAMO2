package com.fao.orderfy.datos.Entidades
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class Cliente(
    @PrimaryKey(autoGenerate = false)
    var idCliente: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "apellido")
    var apellido: String,
    @ColumnInfo(name = "nombreUsuario")
    var nombreUsuario: String,
    @ColumnInfo(name = "pwd")
    var pwd: String
)
