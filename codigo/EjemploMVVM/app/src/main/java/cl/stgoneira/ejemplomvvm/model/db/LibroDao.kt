package cl.stgoneira.ejemplomvvm.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LibroDao {
    @Query("SELECT * FROM productos")
    fun getAll(): List<Libro>

    @Query("SELECT * FROM productos WHERE id = :lid")
    fun findById(lid:Int):Libro

    @Insert
    fun insertAll(vararg libros:Libro)

    @Delete
    fun delete(libro:Libro)
}