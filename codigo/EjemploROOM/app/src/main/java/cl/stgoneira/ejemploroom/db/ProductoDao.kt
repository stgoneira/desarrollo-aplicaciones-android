package cl.stgoneira.ejemploroom.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    fun getAll(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :pid")
    fun findById(pid:Int):Producto

    @Insert
    fun insertAll(vararg productos:Producto)

    @Delete
    fun delete(producto:Producto)

}