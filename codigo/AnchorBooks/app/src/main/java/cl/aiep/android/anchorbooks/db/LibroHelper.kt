package cl.aiep.android.anchorbooks.db

import cl.aiep.android.anchorbooks.modelo.LibroModel

class LibroHelper {
    companion object {
        fun emptyLibroEntity():LibroEntity {
            return LibroEntity(
                0,
                "S/I",
                "S/I",
                "https://via.placeholder.com/140x100",
                "S/I",
                "S/I",
                0,
                "S/I",
                0,
                0,
                0,
                false
            )
        }

        fun emptyLibroModel():LibroModel {
            return LibroModel(
                0,
                "S/I",
                "S/I",
                "https://via.placeholder.com/140x100",
                "S/I",
                "S/I",
                0,
                "S/I",
                0,
                0,
                0,
                false
            )
        }
    }

}