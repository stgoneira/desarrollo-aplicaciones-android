package cl.aiep.android.anchorbooks.service

import cl.aiep.android.anchorbooks.db.LibroDao
import cl.aiep.android.anchorbooks.db.LibroHelper
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.modelo.LibroMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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