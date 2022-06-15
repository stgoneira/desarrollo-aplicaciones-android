package cl.stgoneira.ejemplomvvm.model.db

import androidx.room.Database

abstract class BaseDatos {
    abstract fun libroDao():LibroDao
}