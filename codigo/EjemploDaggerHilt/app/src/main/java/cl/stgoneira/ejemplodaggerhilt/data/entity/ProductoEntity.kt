package cl.stgoneira.ejemplodaggerhilt.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity (
    @PrimaryKey val id:Int,
    @ColumnInfo(name="nombre") val producto:String,
    val precio:Double
)