package cl.aiep.android.anchorbooks.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LibroEntity::class], version = 1)
abstract class BaseDatos :RoomDatabase() {
    abstract fun libroDao():LibroDao
}