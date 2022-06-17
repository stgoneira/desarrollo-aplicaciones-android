package cl.aiep.android.anchorbooks.db

import androidx.room.*

@Dao
interface LibroDao {

    @Query("SELECT * FROM libros")
    fun getAll() : List<LibroEntity>

    @Query("SELECT * FROM libros WHERE id = :lid")
    fun findById(lid:Int):LibroEntity

    @Query("DELETE FROM libros")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg libros:LibroEntity)

    @Delete
    fun delete(libro:LibroEntity)
}