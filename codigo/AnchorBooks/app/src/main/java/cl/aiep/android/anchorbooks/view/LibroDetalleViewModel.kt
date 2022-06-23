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
class LibroDetalleViewModel @Inject constructor(val repo: LibroRepository): ViewModel() {

    val libro = MutableLiveData<Libro>()

    fun cargarLibro(id:Int) {
        viewModelScope.launch {
            val libroFromRepo = repo.findById(id)
            if( libroFromRepo != null ) {
                libro.postValue(libroFromRepo)
            }
        }
    }
}