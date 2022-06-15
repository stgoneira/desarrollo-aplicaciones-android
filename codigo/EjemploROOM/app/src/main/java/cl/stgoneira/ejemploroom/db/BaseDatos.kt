package cl.stgoneira.ejemploroom.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Producto::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun productoDao():ProductoDao
}