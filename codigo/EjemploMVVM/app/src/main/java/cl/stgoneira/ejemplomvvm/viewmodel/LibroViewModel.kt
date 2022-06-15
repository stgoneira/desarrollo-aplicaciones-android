package cl.stgoneira.ejemplomvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.stgoneira.ejemplomvvm.model.LibroRepository
import cl.stgoneira.ejemplomvvm.model.api.LibroModel
import cl.stgoneira.ejemplomvvm.model.db.Libro
import kotlinx.coroutines.launch

class LibroViewModel:ViewModel() {

    val libros = MutableLiveData<List<LibroModel>>()

    fun cargarLibros() {
        viewModelScope.launch {
            val repo = LibroRepository()
            val librosFromRepo = repo.findAll()
            if(!librosFromRepo.isNullOrEmpty()) {
                libros.postValue(librosFromRepo)
            }
        }
    }
}