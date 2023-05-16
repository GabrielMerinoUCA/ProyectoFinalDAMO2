package com.fao.orderfy.datos.local.BD

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room

// TODO: PONER ENTIDADES QUE SE DEBEN DE INCLUIR EN BD
@Database(, version = 1, exportSchema = false)
abstract class BD: RoomDatabase() {
    // TODO: PONER TODOS LOS DAOS PARA BD LOCAL
    abstract fun daoUser(): DaoUser //quitar, obviamente

    /* SINGLETON */
    companion object{
        @Volatile
        private var INSTANCE: BD? = null

        fun getDatabase(context: Context): BD{
            synchronized(this){
                val tempInstance = INSTANCE
                if(tempInstance != null){
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