package cl.stgoneira.ejemplodaggerhilt.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cl.stgoneira.ejemplodaggerhilt.data.entity.ProductoEntity

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    fun getAll(): List<ProductoEntity>

    @Query("SELECT * FROM productos WHERE id = :pid")
    fun findById(pid:Int): ProductoEntity

    @Query("DELETE FROM productos")
    fun deleteAll()

    @Insert
    fun insertAll(vararg productos: ProductoEntity)

    @Delete
    fun delete(producto: ProductoEntity)

}