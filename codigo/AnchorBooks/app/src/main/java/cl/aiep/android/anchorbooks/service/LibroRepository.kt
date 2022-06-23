package cl.aiep.android.anchorbooks.service

import cl.aiep.android.anchorbooks.db.LibroDao
import cl.aiep.android.anchorbooks.helper.LibroHelper
import cl.aiep.android.anchorbooks.mapper.LibroMapper
import cl.aiep.android.anchorbooks.modelo.Libro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LibroRepository @Inject constructor(
    val libroAPI:LibroAPI,
    val libroDao:LibroDao
) {

    suspend fun findById(id:Int):Libro {
        return withContext(Dispatchers.IO) {
            val response = libroAPI.detailLibro(id)
            if( response.isSuccessful ) {
                val libro = response.body() ?: LibroHelper.emptyLibroModel()

                // cachear en BD
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

                // borra cache antigua
                libroDao.deleteAll()
                // cachear datos en BD
                libros.forEach { libroModel ->
                    libroDao.insertAll( LibroMapper.toEntity(libroModel) )
                }

                libros
            }else {
                libroDao.getAll()
            }
        }
    }

}