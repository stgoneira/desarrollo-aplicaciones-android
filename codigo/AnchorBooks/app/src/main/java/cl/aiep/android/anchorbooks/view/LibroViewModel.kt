package cl.aiep.android.anchorbooks.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.aiep.android.anchorbooks.app.AnchorBooksApp
import cl.aiep.android.anchorbooks.modelo.Libro
import cl.aiep.android.anchorbooks.service.LibroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibroViewModel @Inject constructor(val repo: LibroRepository): ViewModel() {

    val libros = MutableLiveData<List<Libro>>()

    fun cargarLibros() {
        viewModelScope.launch {
            val librosFromRepo = repo.findAll()
            if( !librosFromRepo.isNullOrEmpty() ) {
                libros.postValue(librosFromRepo)
            }
        }
    }
}