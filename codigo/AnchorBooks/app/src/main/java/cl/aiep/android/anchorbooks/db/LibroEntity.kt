package cl.aiep.android.anchorbooks.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.aiep.android.anchorbooks.modelo.Libro

@Entity(tableName = "libros")
data class LibroEntity(
    @PrimaryKey override val id:Int,
    override val autor:String,
    override val pais:String,
    override val imagen:String,
    override val lenguaje:String,
    override val enlace:String,
    override val paginas:Int,
    override val titulo:String,
    override val anno:Int,
    override val precio:Int,
    override val ultimoPrecio:Int,
    override val despacho:Boolean
):Libro