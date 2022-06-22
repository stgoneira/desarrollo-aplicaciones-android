package cl.stgoneira.ejemploespresso.data

import cl.stgoneira.ejemploespresso.data.remote.Libro

class LibroHelper {
    companion object {
        fun emptyLibro():Libro {
            return Libro(
                0
                ,"S/I"
                ,"S/I"
                ,"S/I"
                ,"S/I"
                ,"S/I"
                ,0
                ,"S/I"
                ,0
                ,0
                ,0
                ,false
            )
        }
    }
}