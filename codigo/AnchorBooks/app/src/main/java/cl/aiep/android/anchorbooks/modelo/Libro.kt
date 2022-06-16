package cl.aiep.android.anchorbooks.modelo

interface Libro {
    val id: Int
    val autor: String
    val pais: String
    val imagen: String
    val lenguaje: String
    val enlace: String
    val paginas: Int
    val titulo: String
    val anno: Int
    val precio: Int
    val ultimoPrecio: Int
    val despacho: Boolean
}