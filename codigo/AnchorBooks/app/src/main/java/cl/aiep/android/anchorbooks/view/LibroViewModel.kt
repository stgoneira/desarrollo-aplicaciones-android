package cl.aiep.android.anchorbooks.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.aiep.android.anchorbooks.app.AnchorBooksApplication
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.service.LibroRepository
import kotlinx.coroutines.launch

class LibroViewModel(application:Application): AndroidViewModel(application) {

    val libros = MutableLiveData<List<Libro>>()

    fun cargarLibros() {
        viewModelScope.launch {
            val app = getApplication<AnchorBooksApplication>()
            val repo = LibroRepository(app.libroAPI, app.libroDao)
            val librosFromRepo = repo.findAll()
            if( !librosFromRepo.isNullOrEmpty() ) {
                libros.postValue(librosFromRepo)
            }
        }
    }
}