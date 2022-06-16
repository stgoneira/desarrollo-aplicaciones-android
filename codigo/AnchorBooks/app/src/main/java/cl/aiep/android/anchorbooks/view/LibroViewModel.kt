package cl.aiep.android.anchorbooks.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.aiep.android.anchorbooks.modelo.LibroModel
import cl.aiep.android.anchorbooks.service.LibroRepository
import kotlinx.coroutines.launch

class LibroViewModel:ViewModel() {

    val libros = MutableLiveData<List<LibroModel>>()

    fun cargarLibros() {
        viewModelScope.launch {
            val repo = LibroRepository()
            val librosFromRepo = repo.findAll()
            if( !librosFromRepo.isNullOrEmpty() ) {
                libros.postValue(librosFromRepo)
            }
        }
    }
}