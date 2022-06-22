package cl.stgoneira.ejemploespresso.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.stgoneira.ejemploespresso.data.LibroRepository
import cl.stgoneira.ejemploespresso.data.remote.Libro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleLibroViewModel @Inject constructor (private val repository: LibroRepository) : ViewModel() {

    val libro = MutableLiveData<Libro>()

    fun cargarLibro(id:Int) {
        viewModelScope.launch {
            val libroFromRepo = repository.getById(id)
            libro.postValue(libroFromRepo)
        }
    }

}