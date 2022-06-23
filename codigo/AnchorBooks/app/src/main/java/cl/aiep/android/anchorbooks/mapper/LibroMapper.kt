package cl.aiep.android.anchorbooks.mapper

import cl.aiep.android.anchorbooks.db.LibroEntity
import cl.aiep.android.anchorbooks.modelo.Libro

class LibroMapper {
    companion object {
        fun toEntity(libro: Libro):LibroEntity {
            with(libro) {
                return LibroEntity(
                    id, autor, pais, imagen, lenguaje, enlace, paginas, titulo, anno, precio, ultimoPrecio, despacho
                )
            }
        }
    }
}