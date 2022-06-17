package cl.aiep.android.anchorbooks.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.aiep.android.anchorbooks.app.AnchorBooksApplication
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.service.LibroRepository
import kotlinx.coroutines.launch

class LibroDetalleViewModel(application: Application) : AndroidViewModel(application) {

    val libro = MutableLiveData<Libro>()

    fun cargarLibro(libroId:Int) {
        viewModelScope.launch {
            val app = getApplication<AnchorBooksApplication>()
            val repo = LibroRepository(app.libroAPI, app.libroDao)
            val libroFromRepo = repo.findById(libroId)
            libro.postValue(libroFromRepo)
        }
    }

}