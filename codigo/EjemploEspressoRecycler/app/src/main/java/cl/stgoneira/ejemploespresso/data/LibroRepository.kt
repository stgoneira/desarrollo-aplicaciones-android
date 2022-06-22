package cl.stgoneira.ejemploespresso.data

import cl.stgoneira.ejemploespresso.data.remote.Libro
import cl.stgoneira.ejemploespresso.data.remote.LibroRemote
import javax.inject.Inject

class LibroRepository @Inject constructor(
    private val remoteDataSource:LibroRemote
) {

    suspend fun getAll():List<Libro> {
        return remoteDataSource.findAll().body() ?: emptyList()
    }

    suspend fun getById(id:Int):Libro {
        return remoteDataSource.findById(id).body() ?: LibroHelper.emptyLibro()
    }

}