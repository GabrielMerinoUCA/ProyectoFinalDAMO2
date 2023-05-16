package com.fao.orderfy.datos.local.BD

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.fao.orderfy.datos.Entidades.Administrador
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.dao.DaoAdministrador
import com.fao.orderfy.datos.local.dao.DaoCliente
import com.fao.orderfy.datos.local.dao.DaoOrden
import com.fao.orderfy.datos.local.dao.DaoProducto
import com.fao.orderfy.datos.local.dao.DaoRegistro
import com.fao.orderfy.datos.local.dao.DaoTienda
import com.fao.orderfy.datos.local.dao.DaoVendedor

@Database(
    entities = [Administrador::class, Cliente::class, Orden::class, Producto::class,
        Registro::class, Tienda::class, Vendedor::class],
    version = 1,
    exportSchema = false
)

abstract class BD : RoomDatabase() {
    abstract fun DaoAdministrador(): DaoAdministrador
    abstract fun DaoCliente(): DaoCliente
    abstract fun DaoOrden(): DaoOrden
    abstract fun DaoProducto(): DaoProducto
    abstract fun DaoRegistro(): DaoRegistro
    abstract fun DaoTienda(): DaoTienda
    abstract fun DaoVendedor(): DaoVendedor



    /* SINGLETON */
    companion object {
        @Volatile
        private var INSTANCE: BD? = null

        fun getDatabase(context: Context): BD {
            synchronized(this) {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BD::class.java,
                    "OrderFy"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}