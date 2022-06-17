package cl.stgoneira.ejemplodaggerhilt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.stgoneira.ejemplodaggerhilt.data.dao.ProductoDao
import cl.stgoneira.ejemplodaggerhilt.data.entity.ProductoEntity

@Database(entities = [ProductoEntity::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}