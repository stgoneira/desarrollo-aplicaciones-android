package cl.aiep.android.anchorbooks.modelo

import com.google.gson.annotations.SerializedName

data class LibroModel(
    override val id:Int,
    @SerializedName("author") override val autor:String,
    @SerializedName("country") override val pais:String,
    @SerializedName("imageLink") override val imagen:String,
    @SerializedName("language") override val lenguaje:String,
    @SerializedName("link") override val enlace:String?,
    @SerializedName("pages") override val paginas:Int?,
    @SerializedName("title") override val titulo:String,
    @SerializedName("year") override val anno:Int?,
    @SerializedName("price") override val precio:Int?,
    @SerializedName("lastPrice") override val ultimoPrecio:Int?,
    @SerializedName("delivery") override val despacho:Boolean?

):Libro