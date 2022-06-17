package cl.aiep.android.anchorbooks.modelo

import cl.aiep.android.anchorbooks.db.LibroEntity

class LibroMapper {
    companion object {
        fun toEntity(libro:Libro):LibroEntity {
            with(libro){
                return LibroEntity(
                    id, autor, pais, imagen, lenguaje, enlace, paginas, titulo, anno, precio, ultimoPrecio, despacho
                )
            }
        }
    }
}