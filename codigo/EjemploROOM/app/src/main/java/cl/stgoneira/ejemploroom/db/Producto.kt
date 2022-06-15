package cl.stgoneira.ejemploroom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto (
    @PrimaryKey val id:Int,
    @ColumnInfo(name="nombre") val producto:String,
    val precio:Double
)