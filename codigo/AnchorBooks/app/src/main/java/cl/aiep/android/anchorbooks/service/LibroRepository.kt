package cl.aiep.android.anchorbooks.service

import androidx.room.Room
import cl.aiep.android.anchorbooks.db.BaseDatos
import cl.aiep.android.anchorbooks.db.LibroDao
import cl.aiep.android.anchorbooks.db.LibroEntity
import cl.aiep.android.anchorbooks.db.LibroHelper
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.modelo.LibroMapper
import cl.aiep.android.anchorbooks.modelo.LibroModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibroRepository (
    val libroAPI:LibroAPI,
    val libroDao:LibroDao
) {

    suspend fun findById(id:Int):Libro {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.detailLibro(id)
            if( response.isSuccessful ) {
                val libro = response.body() ?: LibroHelper.emptyLibroEntity()
                libroDao.insertAll(LibroMapper.toEntity(libro))
                libro
            } else {
                libroDao.findById(id)
            }
        }
    }

    suspend fun findAll():List<Libro> {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.listLibros()
            if( response.isSuccessful ) {
                val libros = response.body() ?: emptyList()
                libroDao.deleteAll()
                for(libro in libros) {
                    libroDao.insertAll( LibroMapper.toEntity(libro) )
                }
                libros
            } else {
                libroDao.getAll()
            }
        }
    }
}